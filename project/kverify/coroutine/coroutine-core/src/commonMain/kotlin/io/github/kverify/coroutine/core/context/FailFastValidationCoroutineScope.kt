package io.github.kverify.coroutine.core.context

import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.violation.Violation
import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.supervisorScope
import kotlin.coroutines.CoroutineContext

@PublishedApi
internal object AbortValidationException : RuntimeException()

internal class FailFastValidationCoroutineScope(
    coroutineScope: CoroutineScope,
) : ThrowingValidationCoroutineScope {
    override val coroutineContext: CoroutineContext = coroutineScope.coroutineContext

    private val _firstViolation: AtomicRef<Violation?> = atomic(null)

    val firstViolation: Violation?
        get() = _firstViolation.value

    override fun onFailure(violation: Violation): Nothing {
        _firstViolation.compareAndSet(null, violation)

        throw AbortValidationException
    }
}

public suspend fun validateFailFastSuspending(block: suspend ThrowingValidationCoroutineScope.() -> Unit): ValidationResult =
    supervisorScope {
        val context = FailFastValidationCoroutineScope(this)

        try {
            context.block()

            ValidationResult.Valid
        } catch (_: AbortValidationException) {
            ValidationResult(context.firstViolation!!)
        }
    }
