package io.github.kverify.core.verification

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.strategy.ValidationStrategy

public interface ValueVerification<out V, out S : ValidationStrategy> : Verification<S> {
    public val value: V
}

private class ValueVerificationImpl<out V, out S : ValidationStrategy>(
    override val value: V,
    override val validationStrategy: S,
    override val validationContext: ValidationContext,
) : ValueVerification<V, S>

public fun <V, S : ValidationStrategy> ValueVerification(
    value: V,
    validationStrategy: S,
    validationContext: ValidationContext,
): ValueVerification<V, S> =
    ValueVerificationImpl(
        value = value,
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )
