@file:Suppress("TooManyFunctions")

package io.github.kverify.core.scope

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.model.ListValidationContext
import io.github.kverify.core.model.ValidationContext
import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.model.ValidationPathElement
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

@KverifyDsl
public interface ValidationScope {
    public val validationContext: ValidationContext

    public fun onFailure(violation: Violation)
}

public interface Verification<T> {
    public fun enforce(rule: Rule<T>)
}

@PublishedApi
internal class ContextExtendedValidationScope<out T : ValidationScope>(
    val originalValidationScope: T,
    val additionalContext: ValidationContext,
) : ValidationScope by originalValidationScope {
    override val validationContext: ValidationContext
        get() = originalValidationScope.validationContext + additionalContext
}

@PublishedApi
internal class VerificationImpl<T>(
    val value: T,
    scope: ValidationScope,
    path: ValidationPath = emptyList(),
) : Verification<T> {
    private val scope =
        if (path.isNotEmpty()) {
            scope + ListValidationContext(path)
        } else {
            scope
        }

    override fun enforce(rule: Rule<T>): Unit = rule.execute(scope, value)
}

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
    VerificationImpl(
        value = value,
        scope = this,
        path = path,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationScope.verify(
    vararg path: ValidationPathElement,
    value: T,
): Verification<T> =
    VerificationImpl(
        value = value,
        scope = this,
        path = path.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> ValidationScope.verify(value: T): Verification<T> =
    VerificationImpl(
        value = value,
        scope = this,
    )

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> ValidationScope.verify(property: KProperty0<T>): Verification<T> =
    VerificationImpl(
        value = property.get(),
        scope = this,
        path = listOf(ValidationPathElement.Property(property.name)),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationScope.verify(
    vararg path: KProperty<*>,
    property: KProperty0<T>,
): Verification<T> =
    VerificationImpl(
        value = property.get(),
        scope = this,
        path = path.map { ValidationPathElement.Property(it.name) },
    )

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> Verification<T>.with(rule: Rule<T>): Unit = enforce(rule)

@Suppress("NOTHING_TO_INLINE")
public inline operator fun ValidationScope.plus(validationContext: ValidationContext): ValidationScope =
    ContextExtendedValidationScope(
        originalValidationScope = this,
        additionalContext = validationContext,
    )
