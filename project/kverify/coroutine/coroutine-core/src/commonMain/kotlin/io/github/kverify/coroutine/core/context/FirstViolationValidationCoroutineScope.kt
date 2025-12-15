package io.github.kverify.coroutine.core.context

import io.github.kverify.core.context.FirstViolationValidationContext
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.violation.Violation
import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * A [FirstViolationValidationContext] that also implements [CoroutineScope].
 *
 * Records the first [Violation] and attempts to skip subsequent rules in a coroutine context.
 *
 * @see ValidationCoroutineScope
 * @see FirstViolationValidationContext
 * @see verifyFirstSuspending
 */
public interface FirstViolationValidationCoroutineScope :
    ValidationCoroutineScope,
    FirstViolationValidationContext

/**
 * Internal thread-safe implementation of [FirstViolationValidationCoroutineScope].
 *
 * Uses [atomic] operations to record the [firstViolation] in concurrent scenarios.
 *
 * @param coroutineScope The [CoroutineScope] to delegate implementation to.
 */
@PublishedApi
internal class FirstViolationValidationCoroutineScopeImpl(
    coroutineScope: CoroutineScope,
) : FirstViolationValidationCoroutineScope {
    override val coroutineContext: CoroutineContext = coroutineScope.coroutineContext

    /**
     * Atomic reference to the first violation, ensuring thread-safe read/write operations.
     */
    private val atomicFirstViolation: AtomicRef<Violation?> = atomic(null)

    /**
     * The first [Violation] recorded, or `null` if none occurred.
     */
    override val firstViolation: Violation?
        get() = atomicFirstViolation.value

    /**
     * Records the [violation] only if [firstViolation] is `null`.
     *
     * Uses [atomic] [compare-and-set][AtomicRef.compareAndSet] to ensure thread-safety.
     * Subsequent calls are ignored once the [firstViolation] is recorded.
     *
     * @param violation The [Violation] to record
     */
    override fun onFailure(violation: Violation) {
        atomicFirstViolation.compareAndSet(null, violation)
    }
}

/**
 * Validates within a [FirstViolationValidationCoroutineScope] and returns [ValidationResult].
 *
 * Suspending version of [verifyFirst][io.github.kverify.core.context.verifyFirst].
 * Records the first [Violation] and attempts to skip subsequent rules when possible.
 *
 * Returns [ValidationResult.Valid] if no violations occurred,
 * otherwise [ValidationResult.Invalid] with the first violation.
 *
 * @param block The suspending validation block
 * @return [ValidationResult.Valid] or [ValidationResult.Invalid] with the first violation
 * @see io.github.kverify.core.context.verifyFirst
 * @see FirstViolationValidationCoroutineScope
 */
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
