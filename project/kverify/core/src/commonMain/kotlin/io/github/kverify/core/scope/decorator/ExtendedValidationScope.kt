package io.github.kverify.core.scope.decorator

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.scope.ValidationScope

internal class ExtendedValidationScope<out T : ValidationScope>(
    newValidationContext: ValidationContext,
    override val originalValidationScope: T,
) : ValidationScopeDecorator<T> {
    override val validationContext: ValidationContext = originalValidationScope.validationContext + newValidationContext
}

public operator fun ValidationScope.plus(validationContext: ValidationContext): ValidationScope =
    ExtendedValidationScope(
        newValidationContext = validationContext,
        originalValidationScope = this,
    )
