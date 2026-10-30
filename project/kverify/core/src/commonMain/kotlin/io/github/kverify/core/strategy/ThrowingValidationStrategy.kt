package io.github.kverify.core.strategy

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ViolationException
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public interface ThrowingValidationStrategy : ValidationStrategy {
    override fun failWith(violation: Violation): Nothing
}

public fun ThrowingValidationStrategy(): ThrowingValidationStrategy = ThrowingValidationStrategyImpl

public inline fun <R> validateThrowing(
    validationContext: ValidationContext = EmptyValidationContext,
    block: ValidationScope.() -> R,
): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ValidationScope(
        validationStrategy = ThrowingValidationStrategy(),
        validationContext = validationContext,
    ).run(block)
}

private object ThrowingValidationStrategyImpl : ThrowingValidationStrategy {
    override fun failWith(violation: Violation): Nothing = throw ViolationException(violation)
}
