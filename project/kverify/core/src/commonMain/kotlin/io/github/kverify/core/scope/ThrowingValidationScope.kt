package io.github.kverify.core.scope

import io.github.kverify.core.EmptyValidationContext
import io.github.kverify.core.NonReturningValidationScope
import io.github.kverify.core.ThrowingValidationScopeException
import io.github.kverify.core.ValidationContext
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.Violation
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
