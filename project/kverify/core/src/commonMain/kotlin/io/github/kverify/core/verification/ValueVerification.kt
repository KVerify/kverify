package io.github.kverify.core.verification

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.strategy.ValidationStrategy

@KverifyDsl
public interface ValueVerification<out V, out S : ValidationStrategy> : Verification<S> {
    public val value: V
}

private class ValueVerificationImpl<out V, out S : ValidationStrategy>(
    override val value: V,
    override val validationStrategy: S,
) : ValueVerification<V, S>

public fun <V, S : ValidationStrategy> ValueVerification(
    value: V,
    validationStrategy: S,
): ValueVerification<V, S> =
    ValueVerificationImpl(
        value = value,
        validationStrategy = validationStrategy,
    )

context(validationStrategy: S)
public inline fun <V, S : ValidationStrategy> verifyValue(
    value: V,
    block: context(S) ValueVerification<V, S>.() -> Unit,
) {
    val valueVerification =
        ValueVerification(
            value = value,
            validationStrategy = validationStrategy,
        )

    valueVerification.block()
}
