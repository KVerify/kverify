package io.github.kverify.core.verification

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.strategy.ValidationStrategy

public interface Verification<out S : ValidationStrategy> {
    public val validationStrategy: S
    public val validationPath: ValidationPath
}
