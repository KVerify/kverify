package io.github.kverify.core.scope

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.violation.Violation
import kotlin.reflect.KProperty0

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPEALIAS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
public annotation class KverifyDsl

@KverifyDsl
public interface ValidationScope {
    public val validationContext: ValidationContext

    public fun onFailure(violation: Violation)
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

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationScope.verify(value: T): Verification<T> =
    Verification(
        value = value,
        scope = this,
    )

public fun <T> ValidationScope.verify(property: KProperty0<T>): Verification<T> =
    Verification(
        value = property.get(),
        scope = this + ValidationPathElement.Property(property.name),
    )
