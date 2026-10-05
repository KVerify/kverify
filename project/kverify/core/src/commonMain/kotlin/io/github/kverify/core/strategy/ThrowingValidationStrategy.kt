package io.github.kverify.core.strategy

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ViolationException
import io.github.kverify.core.verification.VerificationScope
import io.github.kverify.core.verification.using
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class ThrowingValidationStrategy : ValidationStrategy {
    override fun failWith(violation: Violation): Nothing = throw ViolationException(violation)
}

public inline fun validateThrowing(
    validationContext: ValidationContext = EmptyValidationContext,
    block: context(ThrowingValidationStrategy, ValidationContext) VerificationScope<ThrowingValidationStrategy>.() -> Unit,
) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    VerificationScope(
        validationStrategy = ThrowingValidationStrategy(),
        validationContext = validationContext,
    ).using(block)
}
