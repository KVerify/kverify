@file:Suppress("TooManyFunctions")

package io.github.kverify.core.model

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.violation.Violation
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmName

/**
 * Result of a validation operation.
 *
 * Either [Valid] or [Invalid] containing a list of [Violation]s.
 */
public sealed interface ValidationResult {
    /**
     * `true` when the result is [Valid].
     */
    public val isValid: Boolean

    /**
     * `true` when the result is [Invalid].
     */
    public val isInvalid: Boolean

    /**
     * Represents a successful validation.
     */
    public data object Valid : ValidationResult {
        override val isValid: Boolean = true
        override val isInvalid: Boolean = false

        override fun toString(): String = "ValidationResult.Valid"
    }

    /**
     * Represents a failed validation with collected [violations].
     */
    @JvmInline
    public value class Invalid(
        public val violations: List<Violation>,
    ) : ValidationResult {
        override val isValid: Boolean get() = false
        override val isInvalid: Boolean get() = true

        override fun toString(): String = "ValidationResult.Invalid(violations=$violations)"
    }
}

/**
 * Returns [Violation]s when `this` result is [ValidationResult.Invalid],
 * otherwise an empty list.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationResult.violationsOrEmpty(): List<Violation> =
    if (this is ValidationResult.Invalid) {
        this.violations
    } else {
        emptyList()
    }

/**
 * Returns [Violation]s when `this` result is [ValidationResult.Invalid],
 * otherwise `null`.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationResult.violationsOrNull(): List<Violation>? =
    if (this is ValidationResult.Invalid) {
        this.violations
    } else {
        null
    }

/**
 * Returns [ValidationResult.Valid].
 */
@Suppress("NOTHING_TO_INLINE", "FunctionName")
public inline fun ValidationResult(): ValidationResult.Valid = ValidationResult.Valid

/**
 * Creates a [ValidationResult.Invalid] result from a single [violation].
 */
@Suppress("NOTHING_TO_INLINE", "FunctionName")
public inline fun ValidationResult(violation: Violation): ValidationResult.Invalid =
    ValidationResult.Invalid(
        violations = listOf(violation),
    )

/**
 * Creates a [ValidationResult] from a vararg of [violations].
 * Returns [ValidationResult.Valid] if no violations are provided.
 * [ValidationResult.Invalid] with the provided [violations] otherwise.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationResult(vararg violations: Violation): ValidationResult =
    ValidationResult(
        violations = violations.asList(),
    )

/**
 * Creates a [ValidationResult] from a list of [violations].
 * Returns [ValidationResult.Valid] when the list is empty.
 * [ValidationResult.Invalid] with the provided [violations] otherwise.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationResult(violations: List<Violation>): ValidationResult =
    if (violations.isEmpty()) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(
            violations = violations,
        )
    }

/**
 * Merges two [ValidationResult]s into one, concatenating violations when both are invalid.
 */
public operator fun ValidationResult.plus(other: ValidationResult): ValidationResult =
    when (this) {
        is ValidationResult.Invalid ->
            when (other) {
                is ValidationResult.Invalid -> ValidationResult.Invalid(this.violations + other.violations)
                is ValidationResult.Valid -> this
            }

        is ValidationResult.Valid -> other
    }

/**
 * Appends a single [violation] to `this` result,
 * returning an [ValidationResult.Invalid] with the combined violations.
 */
public operator fun ValidationResult.plus(violation: Violation): ValidationResult.Invalid =
    when (this) {
        is ValidationResult.Invalid ->
            ValidationResult.Invalid(
                violations = this.violations + violation,
            )

        is ValidationResult.Valid ->
            ValidationResult.Invalid(
                violations = listOf(violation),
            )
    }

/**
 * Appends a list of [violations] to `this` result.
 */
@JvmName("plusViolationList")
public operator fun ValidationResult.plus(violations: List<Violation>): ValidationResult {
    if (violations.isEmpty()) return this

    return when (this) {
        is ValidationResult.Invalid ->
            ValidationResult.Invalid(
                violations = this.violations + violations,
            )

        is ValidationResult.Valid ->
            ValidationResult.Invalid(
                violations = violations,
            )
    }
}

/**
 * Adds violations from a list of [validationResults] to `this` result.
 */
@JvmName("plusValidationResultList")
public operator fun ValidationResult.plus(validationResults: List<ValidationResult>): ValidationResult {
    if (validationResults.isEmpty()) return this

    val violations =
        validationResults
            .filterIsInstance<ValidationResult.Invalid>()
            .flatMap { it.violations }

    return this + violations
}

/**
 * Merges a list of [ValidationResult]s into a single result.
 */
public fun List<ValidationResult>.mergeValidationResults(): ValidationResult {
    if (this.isEmpty()) return ValidationResult.Valid

    val violations =
        this
            .filterIsInstance<ValidationResult.Invalid>()
            .flatMap { it.violations }

    return ValidationResult(violations)
}

/**
 * Executes [block] when the result is valid and returns the [block] result or `null`.
 */
public inline fun <T> ValidationResult.ifValid(block: () -> T): T? =
    if (this.isValid) {
        block()
    } else {
        null
    }

/**
 * Executes [block] when the result is invalid and returns the [block] result or `null`.
 */
public inline fun <T> ValidationResult.ifInvalid(block: (List<Violation>) -> T): T? =
    if (this is ValidationResult.Invalid) {
        block(this.violations)
    } else {
        null
    }

/**
 * Folds over the [ValidationResult], invoking [ifValid] or [ifInvalid].
 */
public inline fun <T> ValidationResult.fold(
    ifValid: () -> T,
    ifInvalid: (List<Violation>) -> T,
): T =
    when (this) {
        is ValidationResult.Valid -> ifValid()
        is ValidationResult.Invalid -> ifInvalid(this.violations)
    }

/**
 * Calls [block] when the result is valid and returns the original result.
 */
public inline fun ValidationResult.onValid(block: () -> Unit): ValidationResult {
    if (this.isValid) block()

    return this
}

/**
 * Calls [block] with violations when the result is invalid and returns the original result.
 */
public inline fun ValidationResult.onInvalid(block: (List<Violation>) -> Unit): ValidationResult {
    if (this is ValidationResult.Invalid) block(this.violations)

    return this
}

/**
 * Throws a [ValidationException] if `this` result is invalid.
 *
 * The exception message is built from [violationsOrEmpty] using [transform] and the provided formatting parameters.
 */
@Suppress("LongParameterList")
public fun ValidationResult.throwOnFailure(
    separator: CharSequence = "\n",
    prefix: CharSequence = "Validation failed:\n",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "...",
    onEmptyPrefix: CharSequence = "Validation failed",
    cause: Throwable? = null,
    transform: (Violation) -> String = { "\t- ${it.reason}" },
) {
    if (this !is ValidationResult.Invalid) return

    val prefix =
        if (violations.isEmpty()) {
            onEmptyPrefix
        } else {
            prefix
        }

    val message =
        violations.joinToString(
            separator = separator,
            prefix = prefix,
            postfix = postfix,
            limit = limit,
            truncated = truncated,
            transform = transform,
        )

    throw ValidationException(
        message = message,
        violations = violations,
        cause = cause,
    )
}
