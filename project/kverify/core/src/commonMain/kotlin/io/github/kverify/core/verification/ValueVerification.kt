package io.github.kverify.core.verification

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.strategy.ValidationStrategy

@KverifyDsl
public open class ValueVerification<out V, out S : ValidationStrategy>(
    public val value: V,
    public override val validationStrategy: S,
) : Verification<S>

context(validationStrategy: S)
public fun <V, S : ValidationStrategy> verifyValue(
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
