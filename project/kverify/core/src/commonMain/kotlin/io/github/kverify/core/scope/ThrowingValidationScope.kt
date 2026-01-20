package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ThrowingValidationScopeException
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public interface ThrowingValidationScope : ValidationScope {
    override fun onFailure(violation: Violation): Nothing
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

public class DefaultThrowingValidationScope(
    override val validationContext: ValidationContext = EmptyValidationContext,
) : ThrowingValidationScope {
    override fun onFailure(violation: Violation): Nothing = throw ThrowingValidationScopeException(violation)
}

public inline fun <T> verifyWithThrowing(
    context: ValidationContext = EmptyValidationContext,
    block: DefaultThrowingValidationScope.() -> T,
): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return DefaultThrowingValidationScope(context).block()
}

public inline fun verifyWithFailFast(
    context: ValidationContext = EmptyValidationContext,
    block: DefaultThrowingValidationScope.() -> Unit,
): Violation? =
    try {
        DefaultThrowingValidationScope(context).block()
        null
    } catch (e: ThrowingValidationScopeException) {
        e.violation
    }
