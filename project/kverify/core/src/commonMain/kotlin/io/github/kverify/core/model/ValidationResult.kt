package io.github.kverify.core.model

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.violation.Violation
import kotlin.jvm.JvmInline

/**
 * Represents the result of a validation process, containing a list of [violations].
 */
@JvmInline
public value class ValidationResult(
    public val violations: List<Violation>,
) {
    /**
     * Returns `true` if there are no violations.
     */
    public inline val isValid: Boolean
        get() = violations.isEmpty()

    /**
     * Returns `true` if there is at least one violation.
     */
    public inline val isInvalid: Boolean
        get() = violations.isNotEmpty()

    /**
     * Returns a new result with [violation] added.
     */
    public operator fun plus(violation: Violation): ValidationResult =
        ValidationResult(
            this.violations + violation,
        )

    /**
     * Returns a new result with [violations] added.
     */
    public operator fun <C : Collection<Violation>> plus(violations: C): ValidationResult =
        ValidationResult(
            this.violations + violations,
        )

    /**
     * Combines this result with [other], merging their violations.
     */
    public operator fun plus(other: ValidationResult): ValidationResult =
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

    public companion object {
        /**
         * Represents a successful validation result with no violations.
         */
        public val VALID: ValidationResult = ValidationResult(emptyList())
    }
}

/**
 * Creates a [ValidationResult] from [violations].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationResult(vararg violations: Violation): ValidationResult =
    ValidationResult(
        violations.asList(),
    )

/**
 * Merges this result with [results], combining all violations.
 */
public fun ValidationResult.merge(results: List<ValidationResult>): ValidationResult = this + results.flatMap { it.violations }

/**
 * Merges all results in this list, combining all violations.
 */
public fun List<ValidationResult>.mergeResults(): ValidationResult =
    ValidationResult(
        this.flatMap { it.violations },
    )

/**
 * Ignores specified [violations] in this result, returning a new result with the remaining violations.
 */
public fun ValidationResult.ignore(vararg violations: Violation): ValidationResult =
    ValidationResult(
        this.violations - violations.asList(),
    )

/**
 * Executes [block] if this result is valid.
 *
 * @return the original result.
 * @see ValidationResult.isValid
 */
public inline fun ValidationResult.onValid(block: () -> Unit): ValidationResult {
    if (isValid) block()
    return this
}

/**
 * Executes [block] if this result is invalid, passing the violations.
 *
 * @return the original result.
 * @see ValidationResult.isInvalid
 */
public inline fun ValidationResult.onInvalid(block: (List<Violation>) -> Unit): ValidationResult {
    if (isInvalid) block(violations)
    return this
}

/**
 * @return the result of [onValid] if this result is valid,
 * otherwise returns the result of [onInvalid].
 * @see ValidationResult.isValid
 * @see ValidationResult.isInvalid
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
 * If this result is invalid, uses [joinToString] to generate a message from [ValidationResult.violations]
 * and throws [ValidationException], containing the generated message.
 *
 * @see joinToString
 * @see ValidationResult.isInvalid
 */
@Suppress("LongParameterList")
public fun ValidationResult.throwOnFailure(
    separator: CharSequence = ", ",
    prefix: CharSequence = "Validation failed: [",
    postfix: CharSequence = "]",
    limit: Int = 10,
    truncated: CharSequence = "...",
    cause: Throwable? = null,
    transform: (Violation) -> String = { it.reason },
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
