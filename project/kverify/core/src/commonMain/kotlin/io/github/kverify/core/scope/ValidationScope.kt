package io.github.kverify.core.scope

import io.github.kverify.core.context.IndexPathElement
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.verification.Verification
import io.github.kverify.core.violation.Violation
import kotlin.reflect.KProperty0

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPEALIAS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
public annotation class KverifyDsl

@KverifyDsl
public interface ValidationScope {
    public val validationContext: ValidationContext

    public fun onFailure(violation: Violation)

    public fun enforce(rule: Rule) {
        val violation = rule.check() ?: return

        onFailure(violation)
    }
}

public operator fun ValidationScope.plus(validationContext: ValidationContext): ValidationScope =
    ContextExtendedValidationScope(
        originalValidationScope = this,
        additionalContext = validationContext,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationScope.verify(value: T): Verification<T> =
    Verification(
        value = value,
        scope = this,
    )

public fun <T> ValidationScope.verify(property: KProperty0<T>): Verification<T> =
    Verification(
        value = property.get(),
        scope = this + NamePathElement(property.name),
    )

public inline fun ValidationScope.pathName(
    name: String,
    block: ValidationScope.() -> Unit = {},
): ValidationScope {
    val scope = this + NamePathElement(name)

    return scope.apply(block)
}

public inline fun ValidationScope.pathIndex(
    index: Int,
    block: ValidationScope.() -> Unit = {},
): ValidationScope {
    val scope = this + IndexPathElement(index)

    return scope.apply(block)
}
