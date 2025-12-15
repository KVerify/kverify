package io.github.kverify.coroutine.core.context

import io.github.kverify.core.context.FirstViolationValidationContext
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.violation.Violation
import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

public interface FirstViolationValidationCoroutineScope :
    ValidationCoroutineScope,
    FirstViolationValidationContext

internal open class FirstViolationValidationCoroutineScopeImpl(
@PublishedApi
internal class FirstViolationValidationCoroutineScopeImpl(
    coroutineScope: CoroutineScope,
) : FirstViolationValidationCoroutineScope {
    override val coroutineContext: CoroutineContext = coroutineScope.coroutineContext

    protected val atomicFirstViolation: AtomicRef<Violation?> = atomic(null)
    private val atomicFirstViolation: AtomicRef<Violation?> = atomic(null)

    override val firstViolation: Violation?
        get() = atomicFirstViolation.value

    override fun onFailure(violation: Violation) {
        atomicFirstViolation.compareAndSet(null, violation)
    }
}

public suspend fun validateFirstSuspending(block: suspend FirstViolationValidationCoroutineScope.() -> Unit): ValidationResult =
public suspend inline fun verifyFirstSuspending(
    crossinline block: suspend FirstViolationValidationCoroutineScope.() -> Unit,
): ValidationResult =
    coroutineScope {
        val context = FirstViolationValidationCoroutineScopeImpl(this)

        context.block()

        val firstViolation = context.firstViolation

        if (firstViolation == null) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid(firstViolation)
        }
    }
