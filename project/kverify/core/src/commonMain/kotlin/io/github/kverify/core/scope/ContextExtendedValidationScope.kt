package io.github.kverify.core.scope

import io.github.kverify.core.context.ValidationContext

internal class ContextExtendedValidationScope<out T : ValidationScope>(
    val originalValidationScope: T,
    val additionalContext: ValidationContext,
) : ValidationScope by originalValidationScope {
    override val validationContext: ValidationContext = originalValidationScope.validationContext + additionalContext
}
