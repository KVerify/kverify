package io.github.kverify.core.scope

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.pathName
import io.github.kverify.core.context.plusPathName
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

    public operator fun plus(otherValidationContext: ValidationContext): ValidationScope

    public fun <V> forValue(value: V): ValueValidationScope<V>
}

context(scope: ValidationScope)
public fun ValidationContext.validationScope(): ValidationScope = scope + this

public fun <V> ValidationScope.forProperty(property: KProperty0<V>): ValueValidationScope<V> =
    pathName(property.name)
        .validationScope()
        .forValue(property.get())

context(scope: ValidationScope)
public fun <V> ValidationContext.forValue(value: V): ValueValidationScope<V> = validationScope().forValue(value)

context(scope: ValidationScope)
public fun <V> ValidationContext.forProperty(property: KProperty0<V>): ValueValidationScope<V> =
    this
        .plusPathName(property.name)
        .validationScope()
        .forValue(property.get())

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
