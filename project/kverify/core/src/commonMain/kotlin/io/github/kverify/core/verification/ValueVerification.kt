package io.github.kverify.core.verification

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.strategy.ValidationStrategy

@KverifyDsl
public interface ValueVerification<out V, out S : ValidationStrategy> : Verification<S> {
    public val value: V
}

private class ValueVerificationImpl<out V, out S : ValidationStrategy>(
    override val value: V,
    override val validationStrategy: S,
    override val validationPath: ValidationPath,
) : ValueVerification<V, S>

public fun <V, S : ValidationStrategy> ValueVerification(
    value: V,
    validationStrategy: S,
    validationPath: ValidationPath,
): ValueVerification<V, S> =
    ValueVerificationImpl(
        value = value,
        validationStrategy = validationStrategy,
        validationPath = validationPath,
    )
