package io.github.kverify.core.verification

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.strategy.ValidationStrategy
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public interface Verification<out S : ValidationStrategy> {
    public val validationStrategy: S
    public val validationContext: ValidationContext
}

public inline fun <S : ValidationStrategy, T : Verification<S>> T.using(block: context(S, ValidationContext) T.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    context(validationStrategy, validationContext) { block() }
}
