package io.github.kverify.coroutine.core.context

import io.github.kverify.core.context.ValidationContext
import kotlinx.coroutines.CoroutineScope

public interface ValidationCoroutineScope :
    ValidationContext,
    CoroutineScope
