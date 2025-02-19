@file:Suppress("TooManyFunctions")

package io.github.kverify.core.model

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.violation.Violation
import kotlin.jvm.JvmInline

/**
 * Represents the result of a validation process, containing a list of [violations].
 */
@JvmInline
value class ValidationResult(
    val violations: List<Violation>,
) {
    /**
     * Returns `true` if there are no violations.
     */
    inline val isValid: Boolean
        get() = violations.isEmpty()

    /**
     * Returns `true` if there is at least one violation.
     */
    inline val isInvalid: Boolean
        get() = violations.isNotEmpty()

    /**
     * Returns a new result with [violation] added.
     */
    operator fun plus(violation: Violation): ValidationResult =
        ValidationResult(
            this.violations + violation,
        )

    /**
     * Returns a new result with [violations] added.
     */
    operator fun <C : Collection<Violation>> plus(violations: C): ValidationResult =
        ValidationResult(
            this.violations + violations,
        )

    /**
     * Combines this result with [other], merging their violations.
     */
    operator fun plus(other: ValidationResult): ValidationResult =
        ValidationResult(
            this.violations + other.violations,
        )

    override fun toString(): String =
        if (isValid) {
            "ValidationResult(valid=true)"
        } else {
            "ValidationResult(valid=false, violations=${
                violations.joinToString(
                    separator = ", ",
                    prefix = "[",
                    postfix = "]",
                    limit = 10,
                )
            })"
        }

    companion object {
        /**
         * Represents a successful validation result with no violations.
         */
        val VALID = ValidationResult(emptyList())
    }
}

/**
 * Creates a [ValidationResult] from [violations].
 */
inline fun ValidationResult(vararg violations: Violation): ValidationResult =
    ValidationResult(
        violations.asList(),
    )

/**
 * Merges this result with [results], combining all violations.
 */
fun ValidationResult.merge(results: List<ValidationResult>): ValidationResult = this + results.flatMap { it.violations }

/**
 * Merges all results in this list, combining all violations.
 */
fun List<ValidationResult>.merge(): ValidationResult =
    ValidationResult(
        this.flatMap { it.violations },
    )

/**
 * Executes [block] if this result is valid.
 *
 * @return the original result.
 * @see ValidationResult.isValid
 */
inline fun ValidationResult.onValid(block: () -> Unit): ValidationResult {
    if (isValid) block()
    return this
}

/**
 * Executes [block] if this result is invalid, passing the violations.
 *
 * @return the original result.
 * @see ValidationResult.isInvalid
 */
inline fun ValidationResult.onInvalid(block: (List<Violation>) -> Unit): ValidationResult {
    if (isInvalid) block(violations)
    return this
}

/**
 * @return the result of [onValid] if this result is valid,
 * otherwise returns the result of [onInvalid].
 * @see ValidationResult.isValid
 * @see ValidationResult.isInvalid
 */
inline fun <T> ValidationResult.fold(
    onValid: () -> T,
    onInvalid: (List<Violation>) -> T,
): T =
    if (isValid) {
        onValid()
    } else {
        onInvalid(violations)
    }

/**
 * If this result is invalid, uses [joinToString] to generate message from [ValidationResult.violations]
 * and throws [ValidationException], containing generated message.
 *
 * @see joinToString
 * @see ValidationResult.isInvalid
 */
@Suppress("LongParameterList")
fun ValidationResult.throwOnFailure(
    separator: CharSequence = ", ",
    prefix: CharSequence = "Validation failed: [",
    postfix: CharSequence = "]",
    limit: Int = -1,
    truncated: CharSequence = "...",
    cause: Throwable? = null,
    transform: (Violation) -> String = { it.message },
) {
    if (isValid) return

    throw ValidationException(
        message =
            violations.joinToString(
                separator = separator,
                prefix = prefix,
                postfix = postfix,
                limit = limit,
                truncated = truncated,
                transform = transform,
            ),
        violations = violations,
        cause = cause,
    )
}
