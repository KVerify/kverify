package io.github.kverify.core.scope

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.strategy.ValidationStrategy
import io.github.kverify.core.strategy.failIf
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@KverifyDsl
public interface ValidationScope {
    public val validationStrategy: ValidationStrategy
    public val validationContext: ValidationContext
}

context(validationScope: ValidationScope)
public fun ValidationContext.validationScope(): ValidationScope =
    ValidationScope(
        validationStrategy = validationScope.validationStrategy,
        validationContext = validationScope.validationContext + this,
    )

public fun ValidationScope.failWith(violation: Violation): Unit = validationStrategy.failWith(violation)

public inline fun ValidationScope.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        callsInPlace(lazyViolation, InvocationKind.AT_MOST_ONCE)
    }

    validationStrategy.failIf(condition, lazyViolation)
}

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
) : ValidationScope
