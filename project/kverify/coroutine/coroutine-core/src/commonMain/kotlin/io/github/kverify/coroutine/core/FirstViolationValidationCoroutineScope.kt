package io.github.kverify.coroutine.core

import io.github.kverify.core.EmptyValidationContext
import io.github.kverify.core.FirstViolationValidationScope
import io.github.kverify.core.ValidationContext
import io.github.kverify.core.Violation
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

public class FirstViolationValidationCoroutineScope(
    override val validationContext: ValidationContext = EmptyValidationContext,
    override val coroutineContext: CoroutineContext,
) : FirstViolationValidationScope,
    CoroutineScope {
    private val _firstViolation = atomic<Violation?>(null)

    override val firstViolation: Violation?
        get() = _firstViolation.value

    override fun onFailure(violation: Violation) {
        _firstViolation.compareAndSet(null, violation)
    }
}

public suspend inline fun verifyWithFirstViolationSuspending(
    context: ValidationContext = EmptyValidationContext,
    crossinline block: suspend FirstViolationValidationCoroutineScope.() -> Unit,
): Violation? =
    coroutineScope {
        val validationScope =
            FirstViolationValidationCoroutineScope(
                validationContext = context,
                coroutineContext = coroutineContext,
            )

        validationScope.block()

        validationScope.firstViolation
    }
