package io.github.kverify.core.verification

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.strategy.ValidationStrategy

public interface Verification<out S : ValidationStrategy> {
    public val validationStrategy: S
    public val validationPath: ValidationPath
}

public fun <S : ValidationStrategy, T : Verification<S>> T.using(block: context(S, ValidationPath) T.() -> Unit): Unit =
    context(validationStrategy, validationPath) { block() }
