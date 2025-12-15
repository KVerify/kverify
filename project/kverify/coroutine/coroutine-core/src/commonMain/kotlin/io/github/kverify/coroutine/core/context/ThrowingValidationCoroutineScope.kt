package io.github.kverify.coroutine.core.context

import io.github.kverify.core.context.ThrowingValidationContext
import kotlinx.coroutines.CoroutineScope

/**
 * A [ThrowingValidationContext] that also implements [CoroutineScope].
 *
 * Enables fail-fast validation within coroutine contexts.
 * [onFailure] must not return normally (it should throw an exception or terminate execution).
 */
public interface ThrowingValidationCoroutineScope :
    ValidationCoroutineScope,
    ThrowingValidationContext
