package io.github.kverify.core.scope

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.strategy.ValidationStrategy
import io.github.kverify.core.strategy.failIf
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@KverifyDsl
public interface ValidationScope<out S : ValidationStrategy> {
    public val validationStrategy: S
    public val validationContext: ValidationContext
}

context(validationScope: ValidationScope<S>)
public fun <S : ValidationStrategy> ValidationContext.verify(): ValidationScope<S> =
    ValidationScope(
        validationStrategy = validationScope.validationStrategy,
        validationContext = validationScope.validationContext + this,
    )

public fun <S : ValidationStrategy> ValidationScope<S>.failWith(violation: Violation): Unit = validationStrategy.failWith(violation)

public inline fun <S : ValidationStrategy> ValidationScope<S>.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        callsInPlace(lazyViolation, InvocationKind.AT_MOST_ONCE)
    }

    validationStrategy.failIf(condition, lazyViolation)
}

public fun <S : ValidationStrategy> ValidationScope(
    validationStrategy: S,
    validationContext: ValidationContext,
): ValidationScope<S> =
    ValidationScopeImpl(
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )

private class ValidationScopeImpl<out S : ValidationStrategy>(
    override val validationStrategy: S,
    override val validationContext: ValidationContext,
) : ValidationScope<S>
