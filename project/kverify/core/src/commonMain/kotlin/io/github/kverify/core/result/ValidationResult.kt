package io.github.kverify.core.result

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.violation.Violation
import kotlin.jvm.JvmInline

/**
 * The outcome of a validation run.
 *
 * A [ValidationResult] is valid when [violations] is empty, and invalid otherwise.
 *
 * @param violations All violations produced during the validation run. Empty if validation passed.
 */
@JvmInline
public value class ValidationResult(
    public val violations: List<Violation>,
) {
    /**
     * `true` if [violations] is empty (validation passed).
     */
    public inline val isValid: Boolean get() = violations.isEmpty()

    /**
     * `true` if [violations] is not empty (validation failed).
     */
    public inline val isInvalid: Boolean get() = !isValid
}

/**
 * Executes [block] if this result is valid (no violations).
 */
public inline fun ValidationResult.onValid(block: () -> Unit) {
    if (isValid) block()
}

/**
 * Executes [block] with [ValidationResult.violations] if this result is invalid (at least one violation).
 */
public inline fun ValidationResult.onInvalid(block: (List<Violation>) -> Unit) {
    if (isInvalid) block(violations)
}

/**
 * Returns the result of [onValid] if this result is valid,
 * or the result of [onInvalid] with [ValidationResult.violations] if this result is invalid.
 *
 * Exactly one of the two lambdas is invoked.
 */
public inline fun <T> ValidationResult.fold(
    onValid: () -> T,
    onInvalid: (List<Violation>) -> T,
): T =
    if (isValid) {
        onValid()
    } else {
        onInvalid(violations)
    }

/**
 * Throws a [ValidationException] carrying all [ValidationResult.violations] if this result is invalid.
 *
 * Does nothing if this result is valid.
 *
 * @throws ValidationException if [ValidationResult.isInvalid] is `true`.
 */
public fun ValidationResult.throwOnInvalid(): Unit =
    onInvalid {
        throw ValidationException(it)
    }
