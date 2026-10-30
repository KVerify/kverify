package io.github.kverify.core.scope

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.pathName
import io.github.kverify.core.strategy.ValidationStrategy
import io.github.kverify.core.strategy.failIf
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KProperty0

@KverifyDsl
public interface ValidationScope {
    public val validationStrategy: ValidationStrategy
    public val validationContext: ValidationContext

    public fun ValidationContext.validationScope(): ValidationScope

    public fun <V> forValue(value: V): ValueValidationScope<V>

    public fun <V> forProperty(property: KProperty0<V>): ValueValidationScope<V>
}

public inline fun ValidationScope.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        callsInPlace(lazyViolation, InvocationKind.AT_MOST_ONCE)
    }

    return validationStrategy.failIf(condition, lazyViolation)
}

public fun ValidationScope.failWith(violation: Violation): Unit = validationStrategy.failWith(violation)

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
    override fun ValidationContext.validationScope(): ValidationScopeImpl =
        ValidationScopeImpl(
            validationStrategy = validationStrategy,
            validationContext = validationContext + this,
        )

    override fun <V> forValue(value: V): ValueValidationScope<V> =
        ValueValidationScope(
            value = value,
            validationStrategy = validationStrategy,
            validationContext = validationContext,
        )

    override fun <V> forProperty(property: KProperty0<V>): ValueValidationScope<V> =
        ValueValidationScope(
            value = property.get(),
            validationStrategy = validationStrategy,
            validationContext = validationContext.pathName(property.name),
        )
}
