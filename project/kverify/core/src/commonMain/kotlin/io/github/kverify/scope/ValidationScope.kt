package io.github.kverify.scope

import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.core.ScopedVerification
import io.github.kverify.core.Verification
import io.github.kverify.rule.Violation
import kotlin.contracts.contract
import kotlin.reflect.KProperty0

/**
 * Marks DSL elements in the Kverify API to prevent scope leakage.
 *
 * This annotation prevents implicit receiver mixing in nested DSL blocks.
 *
 * @see DslMarker
 */
@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPEALIAS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
public annotation class KverifyDsl

@KverifyDsl
public interface ValidationScope {
    public val validationContext: ValidationContext

    public fun onFailure(violation: Violation)
}

public interface NonReturningValidationScope : ValidationScope {
    override fun onFailure(violation: Violation): Nothing
}

@PublishedApi
internal class ContextExtendedValidationScope<out T : ValidationScope>(
    val originalValidationScope: T,
    val additionalContext: ValidationContext,
) : ValidationScope by originalValidationScope {
    override val validationContext: ValidationContext
        get() = originalValidationScope.validationContext + additionalContext
}

@Suppress("NOTHING_TO_INLINE")
public inline operator fun ValidationScope.plus(validationContext: ValidationContext): ValidationScope =
    ContextExtendedValidationScope(
        originalValidationScope = this,
        additionalContext = validationContext,
    )

public inline fun ValidationScope.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    if (condition) {
        val violation = lazyViolation()
        onFailure(violation)
    }
}

public inline fun NonReturningValidationScope.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        returns() implies !condition
    }

    if (condition) {
        val violation = lazyViolation()
        onFailure(violation)
    }
}

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> ValidationScope.verify(value: T): ScopedVerification<T> =
    ScopedVerification(
        value = value,
        scope = this,
    )

public infix fun <T> ValidationScope.verify(property: KProperty0<T>): ScopedVerification<T> {
    val pathElement = ValidationPathElement.Property(property.name)
    val context = ListValidationContext(pathElement)

    return ScopedVerification(
        value = property.get(),
        scope = this + context,
    )
}

public fun <T> ValidationScope.verify(
    pathElements: List<ValidationPathElement>,
    value: T,
): Verification<T> {
    val newScope =
        if (pathElements.isNotEmpty()) {
            this + ListValidationContext(pathElements)
        } else {
            this
        }

    return ScopedVerification(
        value = value,
        scope = newScope,
    )
}
