package io.github.kverify.core.scope

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.strategy.ValidationStrategy
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@KverifyDsl
public interface VerificationScope<out S : ValidationStrategy> : ValidationScope {
    public val validationStrategy: S
}

private class VerificationScopeImpl<out S : ValidationStrategy>(
    override val validationStrategy: S,
    override val validationContext: ValidationContext,
) : VerificationScope<S>

public fun <S : ValidationStrategy> VerificationScope(
    validationStrategy: S,
    validationContext: ValidationContext,
): VerificationScope<S> =
    VerificationScopeImpl(
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )

public inline fun <S : ValidationStrategy, T : VerificationScope<S>> T.using(block: context(S, ValidationContext) T.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    context(validationStrategy, validationContext) { block() }
}
