package io.github.kverify.coroutine.core.context

import io.github.kverify.core.context.ThrowingValidationContext
import kotlinx.coroutines.CoroutineScope

public interface ThrowingValidationCoroutineScope :
    ThrowingValidationContext,
    CoroutineScope
