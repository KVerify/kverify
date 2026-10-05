package io.github.kverify.core.verification

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.strategy.ValidationStrategy
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@KverifyDsl
public interface Verification<out S : ValidationStrategy> {
    public val validationStrategy: S
    public val validationContext: ValidationContext
}

private class VerificationImpl<out S : ValidationStrategy>(
    override val validationStrategy: S,
    override val validationContext: ValidationContext,
) : Verification<S>

public fun <S : ValidationStrategy> Verification(
    validationStrategy: S,
    validationContext: ValidationContext,
): Verification<S> =
    VerificationImpl(
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )

public inline fun <S : ValidationStrategy, T : Verification<S>> T.using(block: context(S, ValidationContext) T.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    context(validationStrategy, validationContext) { block() }
}
