package io.github.kverify.core.scope

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.pathName
import io.github.kverify.core.strategy.ValidationStrategy
import io.github.kverify.core.strategy.failIf
import io.github.kverify.core.violation.Violation
import kotlin.contracts.ExperimentalExtendedContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KProperty0

@KverifyDsl
public interface ValidationScope {
    public val validationStrategy: ValidationStrategy
    public val validationContext: ValidationContext

    public operator fun plus(otherValidationContext: ValidationContext): ValidationScope

    public fun <V> forValue(value: V): ValueValidationScope<V>
}

/**
 * Returns a [ValueValidationScope] for the value of [property], with the property name
 * added to this scope as a [pathName].
 */
public fun <V> ValidationScope.forProperty(property: KProperty0<V>): ValueValidationScope<V> =
    plus(
        pathName(property.name),
    ).forValue(
        property.get(),
    )

/**
 * Reports the violation from [lazyViolation] if [condition] is `true`.
 */
@OptIn(ExperimentalExtendedContracts::class)
public inline fun ValidationScope.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        callsInPlace(lazyViolation, InvocationKind.AT_MOST_ONCE)
        condition.holdsIn(lazyViolation)
    }

    return validationStrategy.failIf(condition, lazyViolation)
}

/**
 * Passes [violation] to the [ValidationScope.validationStrategy] of this scope.
 */
public fun ValidationScope.failWith(violation: Violation): Unit = validationStrategy.failWith(violation)

/**
 * Returns a [ValidationScope] with the given [validationStrategy] and [validationContext].
 */
public fun ValidationScope(
    validationStrategy: ValidationStrategy,
    validationContext: ValidationContext,
): ValidationScope =
    ValidationScopeImpl(
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )

private class ValidationScopeImpl(
    override val validationStrategy: ValidationStrategy,
    override val validationContext: ValidationContext,
) : ValidationScope {
    override fun plus(otherValidationContext: ValidationContext): ValidationScope =
        ValidationScopeImpl(
            validationStrategy = validationStrategy,
            validationContext = validationContext + otherValidationContext,
        )

    override fun <V> forValue(value: V): ValueValidationScope<V> =
        ValueValidationScope(
            value = value,
            validationStrategy = validationStrategy,
            validationContext = validationContext,
        )
}
