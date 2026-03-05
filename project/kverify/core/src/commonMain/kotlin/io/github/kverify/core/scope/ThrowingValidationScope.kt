package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.result.ViolationException
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class ThrowingValidationScope(
    public override val validationContext: ValidationContext,
) : ValidationScope {
    override fun onFailure(violation: Violation): Nothing = throw ViolationException(violation)
}

public inline fun <T> verifyThrowing(
    validationContext: ValidationContext = EmptyValidationContext,
    block: ThrowingValidationScope.() -> T,
): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ThrowingValidationScope(validationContext).run(block)
}
