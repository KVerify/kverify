package io.github.kverify.core.scope.decorator

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.scope.ValidationScope

internal class ExtendedValidationScope<out T : ValidationScope>(
    override val originalValidationScope: T,
    newValidationContext: ValidationContext,
) : ValidationScopeDecorator<T> {
    override val validationContext: ValidationContext = originalValidationScope.validationContext + newValidationContext
}

public operator fun <T : ValidationScope> T.plus(validationContext: ValidationContext): ValidationScopeDecorator<T> =
    ExtendedValidationScope(
        originalValidationScope = this,
        newValidationContext = validationContext,
    )
