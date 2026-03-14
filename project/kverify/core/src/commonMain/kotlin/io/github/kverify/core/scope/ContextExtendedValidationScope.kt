package io.github.kverify.core.scope

import io.github.kverify.core.context.ValidationContext

/**
 * A [ValidationScope] that wraps an existing scope with an extended [ValidationContext].
 *
 * All rule enforcement is delegated to [originalValidationScope] unchanged — only the
 * [validationContext] differs, combining the original context with [additionalContext].
 *
 * This is the backing implementation of [ValidationScope.plus] and is not intended to be
 * instantiated directly.
 */
internal class ContextExtendedValidationScope<out T : ValidationScope>(
    val originalValidationScope: T,
    val additionalContext: ValidationContext,
) : ValidationScope by originalValidationScope {
    override val validationContext: ValidationContext = originalValidationScope.validationContext + additionalContext
}
