package io.github.kverify.core.scope

import io.github.kverify.core.context.ValidationContext

public interface ValidationScope {
    public val validationContext: ValidationContext
}
