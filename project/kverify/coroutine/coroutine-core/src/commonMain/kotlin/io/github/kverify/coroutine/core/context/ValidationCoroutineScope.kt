package io.github.kverify.coroutine.core.context

import io.github.kverify.core.context.ValidationContext
import kotlinx.coroutines.CoroutineScope

/**
 * A [ValidationContext] that also implements [CoroutineScope].
 *
 * Enables validation logic to be used within coroutine contexts,
 * combining the validation DSL with coroutine capabilities.
 *
 * @see FirstViolationValidationCoroutineScope
 * @see ThrowingValidationCoroutineScope
 */
public interface ValidationCoroutineScope :
    ValidationContext,
    CoroutineScope
