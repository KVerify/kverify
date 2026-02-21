package io.github.kverify.scope

import io.github.kverify.context.EmptyValidationContext
import io.github.kverify.context.ValidationContext
import io.github.kverify.result.ThrowingValidationScopeException
import io.github.kverify.rule.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

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
