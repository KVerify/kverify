package io.github.kverify.coroutine.core.context

import io.github.kverify.core.context.ThrowingValidationContext

public interface ThrowingValidationCoroutineScope :
    ValidationCoroutineScope,
    ThrowingValidationContext
