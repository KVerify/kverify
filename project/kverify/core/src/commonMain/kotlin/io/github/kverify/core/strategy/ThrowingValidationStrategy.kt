package io.github.kverify.core.strategy

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ViolationException
import io.github.kverify.core.scope.VerificationScope
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class ThrowingValidationStrategy : ValidationStrategy {
    override fun failWith(violation: Violation): Nothing = throw ViolationException(violation)
}

public inline fun <R> validateThrowing(
    validationContext: ValidationContext = EmptyValidationContext,
    block: VerificationScope<ThrowingValidationStrategy>.() -> R,
): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return VerificationScope(
        validationStrategy = ThrowingValidationStrategy(),
        validationContext = validationContext,
    ).run(block)
}
