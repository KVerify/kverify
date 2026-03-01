package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ThrowingValidationScopeException
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class ThrowingValidationScope(
    override val validationContext: ValidationContext = EmptyValidationContext,
) : ValidationScope {
    override fun onFailure(violation: Violation): Nothing = throw ThrowingValidationScopeException(violation)
}

public inline fun ThrowingValidationScope.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        returns() implies !condition
    }

    if (condition) {
        val violation = lazyViolation()
        onFailure(violation)
    }
}

public inline fun <T> verifyWithThrowing(
    validationContext: ValidationContext = EmptyValidationContext,
    block: ThrowingValidationScope.() -> T,
): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ThrowingValidationScope(validationContext).run(block)
}
