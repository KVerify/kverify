package io.github.kverify.coroutine.core.context

import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.violation.Violation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.supervisorScope

@PublishedApi
internal object AbortValidationException : RuntimeException()

internal class FailFastValidationCoroutineScope(
    coroutineScope: CoroutineScope,
) : FirstViolationValidationCoroutineScopeImpl(coroutineScope),
    ThrowingValidationCoroutineScope {
    override fun onFailure(violation: Violation): Nothing {
        super.onFailure(violation)

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
