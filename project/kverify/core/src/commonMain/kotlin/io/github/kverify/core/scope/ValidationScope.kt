package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.IndexPathElement
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.reflect.KProperty0

/**
 * DSL marker for the KVerify validation DSL.
 */
@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPEALIAS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
public annotation class KverifyDsl

/**
 * The entry point for enforcing validation rules.
 *
 * A [ValidationScope] determines what happens when a [Rule] fails. Built-in implementations
 * include [CollectingValidationScope] and [ThrowingValidationScope].
 *
 * The scope also carries a [validationContext],
 * allowing contextual data such as path segments to be attached to violations.
 *
 * Scopes are extended - not mutated - by [plus], which returns a new scope with additional
 * context layered on top. The original scope is always left unchanged.
 */
@KverifyDsl
public interface ValidationScope {
    /**
     * The context currently associated with this scope.
     *
     * Rules enforced by this scope can read this context to attach path or other metadata
     * to the violations they produce.
     */
    public val validationContext: ValidationContext

    /**
     * Enforces [rule] according to this scope's implementation.
     */
    public fun enforce(rule: Rule)

    /**
     * Returns a new scope with [validationContext] merged into the current context.
     *
     * If [validationContext] is [EmptyValidationContext], returns this scope unchanged.
     */
    public operator fun plus(validationContext: ValidationContext): ValidationScope =
        if (validationContext !== EmptyValidationContext) {
            ContextExtendedValidationScope(
                originalValidationScope = this,
                additionalContext = validationContext,
            )
        } else {
            this
        }
}

/**
 * Creates a [Verification] for [value] within this scope.
 *
 * The [Verification] binds [value] to this scope so that validation rules
 * can be applied to it as extension functions.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationScope.verify(value: T): Verification<T> =
    Verification(
        value = value,
        scope = this,
    )

/**
 * Creates a [Verification] for [property], automatically appending its [name][KProperty0.name] to the validation path.
 *
 * The property name is appended as a [NamePathElement] to the context before rules are enforced,
 * so any violations produced will carry the property name in their path.
 */
public fun <T> ValidationScope.verify(property: KProperty0<T>): Verification<T> =
    Verification(
        value = property.get(),
        scope = this + NamePathElement(property.name),
    )

/**
 * Creates and enforces a [Rule] that fails when [predicate] returns `true`.
 *
 * If [predicate] returns `false`, no violation is produced and [lazyViolation] is never called.
 */
public inline fun ValidationScope.failIf(
    crossinline predicate: () -> Boolean,
    crossinline lazyViolation: () -> Violation,
): Unit =
    enforce {
        if (predicate()) lazyViolation() else null
    }

/**
 * Returns a new scope with [name] appended to the validation path as a [NamePathElement].
 *
 * If [block] is provided, it is run immediately in the new scope before the scope is returned.
 * The original scope is not modified.
 */
public inline fun ValidationScope.pathName(
    name: String,
    block: ValidationScope.() -> Unit = {},
): ValidationScope {
    val scope = this + NamePathElement(name)

    return scope.apply(block)
}

/**
 * Returns a new scope with [index] appended to the validation path as an [IndexPathElement].
 *
 * If [block] is provided, it is run immediately in the new scope before the scope is returned.
 * The original scope is not modified.
 */
public inline fun ValidationScope.pathIndex(
    index: Int,
    block: ValidationScope.() -> Unit = {},
): ValidationScope {
    val scope = this + IndexPathElement(index)

    return scope.apply(block)
}
