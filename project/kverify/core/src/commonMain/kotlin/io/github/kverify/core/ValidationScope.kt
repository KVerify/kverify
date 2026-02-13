@file:Suppress("TooManyFunctions")

package io.github.kverify.core

import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

@KverifyDsl
public interface ValidationScope {
    public val validationContext: ValidationContext

    public fun onFailure(violation: Violation)
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

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationScope.verify(
    path: ValidationPath,
    value: T,
): Verification<T> =
    ScopedVerification(
        value = value,
        scope = this,
        path = path,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationScope.verify(
    vararg path: ValidationPathElement,
    value: T,
): Verification<T> = verify(path.asList(), value)

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> ValidationScope.verify(value: T): Verification<T> =
    ScopedVerification(
        value = value,
        scope = this,
    )

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> ValidationScope.verify(property: KProperty0<T>): Verification<T> =
    ScopedVerification(
        value = property.get(),
        scope = this,
        path = listOf(ValidationPathElement.Property(property.name)),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationScope.verify(
    vararg path: KProperty<*>,
    property: KProperty0<T>,
): Verification<T> =
    ScopedVerification(
        value = property.get(),
        scope = this,
        path = path.map { ValidationPathElement.Property(it.name) },
    )
