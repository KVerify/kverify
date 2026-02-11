package io.github.kverify.core

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public interface NonReturningValidationScope : ValidationScope {
    override fun onFailure(violation: Violation): Nothing
}

public inline fun NonReturningValidationScope.failIf(
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

public class ThrowingValidationScope(
    override val validationContext: ValidationContext = EmptyValidationContext,
) : NonReturningValidationScope {
    override fun onFailure(violation: Violation): Nothing = throw ThrowingValidationScopeException(violation)
}

public inline fun <T> verifyWithThrowing(
    context: ValidationContext = EmptyValidationContext,
    block: ThrowingValidationScope.() -> T,
): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ThrowingValidationScope(context).block()
}

public inline fun verifyWithFailFast(
    context: ValidationContext = EmptyValidationContext,
    block: ThrowingValidationScope.() -> Unit,
): Violation? =
    try {
        ThrowingValidationScope(context).block()
        null
    } catch (e: ThrowingValidationScopeException) {
        e.violation
    }
