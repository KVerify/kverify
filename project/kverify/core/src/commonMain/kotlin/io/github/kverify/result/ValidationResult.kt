@file:Suppress("TooManyFunctions")

package io.github.kverify.result

import io.github.kverify.rule.Violation
import kotlin.js.JsName
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmName

/**
 * The result of a validation operation.
 *
 * @see Valid
 * @see Invalid
 */
public sealed interface ValidationResult {
    /**
     * Returns `true` if `this` result is [Valid].
     */
    public val isValid: Boolean

    /**
     * Returns `true` if `this` result is [Invalid].
     */
    public val isInvalid: Boolean

    /**
     * Represents a successful validation with no violations.
     */
    public data object Valid : ValidationResult {
        override val isValid: Boolean = true
        override val isInvalid: Boolean = false

        override fun toString(): String = "ValidationResult.Valid"
    }

    /**
     * Represents a failed validation with a list of [violations].
     *
     * @param violations The list of violations that caused the validation to fail.
     */
    @JvmInline
    public value class Invalid(
        public val violations: List<Violation>,
    ) : ValidationResult {
        public constructor(violation: Violation) : this(listOf(violation))
        public constructor(vararg violations: Violation) : this(violations.asList())

        override val isValid: Boolean get() = false
        override val isInvalid: Boolean get() = true

        override fun toString(): String = "ValidationResult.Invalid(violations=$violations)"
    }
}

/**
 * @return the [violations][ValidationResult.Invalid.violations] if `this` is [ValidationResult.Invalid],
 * or an empty list otherwise.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationResult.violationsOrEmpty(): List<Violation> =
    if (this is ValidationResult.Invalid) {
        this.violations
    } else {
        emptyList()
    }

/**
 * @return the [violation list][ValidationResult.Invalid.violations] if `this` is [ValidationResult.Invalid],
 * or `null` otherwise.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationResult.violationsOrNull(): List<Violation>? =
    if (this is ValidationResult.Invalid) {
        this.violations
    } else {
        null
    }

/**
 * @return [ValidationResult.Valid].
 */
@JsName("ValidationResultValid")
@Suppress("NOTHING_TO_INLINE", "FunctionName")
public inline fun ValidationResult(): ValidationResult.Valid = ValidationResult.Valid

/**
 * @return A new [ValidationResult.Invalid] result with a single [violation].
 */
@JsName("ValidationResultInvalidSingle")
@Suppress("NOTHING_TO_INLINE", "FunctionName")
public inline fun ValidationResult(violation: Violation): ValidationResult.Invalid = ValidationResult.Invalid(violation)

/**
 * @return A new [ValidationResult.Invalid] result with the given [violations].
 */
@JsName("ValidationResultInvalidVararg")
@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationResult(vararg violations: Violation): ValidationResult =
    ValidationResult(
        violations = violations.asList(),
    )

/**
 * @return [ValidationResult.Valid] if [violations] is empty, otherwise [ValidationResult.Invalid].
 */
@JsName("ValidationResultInvalidList")
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
 * @return A new [ValidationResult.Invalid] result with `this` [Violation].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun Violation.toValidationResult(): ValidationResult.Invalid = ValidationResult.Invalid(this)

/**
 * @return [ValidationResult.Valid] if `this` is `null`, otherwise [ValidationResult.Invalid].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun Violation?.toValidationResult(): ValidationResult =
    if (this == null) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(this)
    }

/**
 * Combines `this` result with [other] result.
 *
 * 1. If both results are [ValidationResult.Invalid], returns a new [ValidationResult.Invalid]
 * with combined [violations][ValidationResult.Invalid.violations].
 *
 * 2. If one result is [ValidationResult.Valid], returns the other result.
 *
 * 3. If both results are [ValidationResult.Valid], returns [ValidationResult.Valid].
 *
 *
 * ### Example:
 * ```kt
 * val invalid = ValidationResult.Invalid(violation1)
 * val invalid2 = ValidationResult.Invalid(violation2)
 * val valid = ValidationResult.Valid
 *
 * invalid + invalid2 // ValidationResult.Invalid(violation1, violation2)
 * invalid + valid // invalid
 * valid + invalid // invalid
 * valid + valid // valid
 * ```
 *
 * @param other The [ValidationResult] to combine with
 * @return The combined [ValidationResult]
 */
public operator fun ValidationResult.plus(other: ValidationResult): ValidationResult =
    when (this) {
        is ValidationResult.Invalid -> {
            when (other) {
                is ValidationResult.Invalid -> ValidationResult.Invalid(this.violations + other.violations)
                is ValidationResult.Valid -> this
            }
        }

        is ValidationResult.Valid -> {
            other
        }
    }

/**
 * Adds a [violation] to `this` result.
 *
 * Returns a new [ValidationResult.Invalid] with the [violation]
 * added to the [violation list][ValidationResult.Invalid.violations].
 * If `this` is [ValidationResult.Valid], returns a new [ValidationResult.Invalid]
 * with the [violation] as the only violation in the [list][ValidationResult.Invalid.violations].
 *
 * ### Example:
 * ```kt
 * val invalid = ValidationResult.Invalid(violation1)
 * val valid = ValidationResult.Valid
 *
 * // ValidationResult.Invalid(violation1, violation2)
 * invalid + violation2
 *
 * // ValidationResult.Invalid(violation)
 * valid + violation
 * ```
 *
 * @param violation The [Violation] to add
 * @return A [ValidationResult.Invalid] with the added violation
 */
public operator fun ValidationResult.plus(violation: Violation): ValidationResult.Invalid =
    when (this) {
        is ValidationResult.Invalid -> {
            ValidationResult.Invalid(
                violations = this.violations + violation,
            )
        }

        is ValidationResult.Valid -> {
            ValidationResult.Invalid(
                violations = listOf(violation),
            )
        }
    }

/**
 * Adds a list of [violations] to `this` result.
 *
 * 1. If [violations] is empty, returns `this` result unchanged.
 *
 * 2. If `this` is [ValidationResult.Invalid], returns a new [ValidationResult.Invalid]
 * with the original [violation list][ValidationResult.Invalid.violations] combined with the given [violations].
 *
 * 3. If `this` is [ValidationResult.Valid], returns a new [ValidationResult.Invalid]
 * with given [violations].
 *
 * ### Example:
 * ```kt
 * val violations = listOf(violation3, violation4)
 * val invalid = ValidationResult.Invalid(violations1, violations2)
 * val valid = ValidationResult.Valid
 *
 * // ValidationResult.Invalid(violation1, violation2, violation3, violation4)
 * invalid + violations
 *
 * // ValidationResult.Invalid(violation3, violation4)
 * valid + violations
 * ```
 *
 * @param violations The list of [Violation]s to add
 * @return The updated [ValidationResult]
 */
@JvmName("plusViolationList")
public operator fun ValidationResult.plus(violations: List<Violation>): ValidationResult {
    if (violations.isEmpty()) return this

    return when (this) {
        is ValidationResult.Invalid -> {
            ValidationResult.Invalid(
                violations = this.violations + violations,
            )
        }

        is ValidationResult.Valid -> {
            ValidationResult.Invalid(
                violations = violations,
            )
        }
    }
}

/**
 * Adds all violations from a list of [validationResults] to `this` result.
 *
 * Flattens all violations from [validationResults] and adds them to `this` result.
 *
 * 1. If [validationResults] is empty, returns `this` result unchanged.
 *
 * 2. If [validationResults] contains only [ValidationResult.Valid], returns `this` result unchanged.
 *
 * 3. If `this` is [ValidationResult.Valid] and [validationResults] contains at least one [ValidationResult.Invalid],
 * returns a new [ValidationResult.Invalid] with the flattened violations
 * found in [validationResults].
 *
 * 4. If `this` is [ValidationResult.Invalid] and [validationResults] contains at least one [ValidationResult.Invalid],
 * returns a new [ValidationResult.Invalid] with original [violations][ValidationResult.Invalid.violations]
 * combined with the flattened violations found in [validationResults].
 *
 * ### Example:
 * ```kt
 * val invalid1 = ValidationResult.Invalid(violations1, violations2)
 * val invalid2 = ValidationResult.Invalid(violations3, violations4)
 * val invalid4 = ValidationResult.Invalid(violations5, violations6)
 * val valid = ValidationResult.Valid
 * val validationResults = listOf(invalid2, valid, invalid3)
 *
 * // ValidationResult.Invalid(violation1, violation2, violation3, violation4, violation5, violation6)
 * invalid1 + validationResults
 *
 * // ValidationResult.Invalid(violation3, violation4, violation5, violation6)
 * valid + validationResults
 * ```
 *
 * @param validationResults The list of [ValidationResult]s to extract violations from
 * @return The updated [ValidationResult]
 */
@JvmName("plusValidationResultList")
public operator fun ValidationResult.plus(validationResults: List<ValidationResult>): ValidationResult {
    val violations =
        validationResults
            .getViolations()
            .ifEmpty { return this }

    return this + violations
}

/**
 * Merges all results in `this` list into a single [ValidationResult].
 *
 * 1. If `this` list is empty, returns [ValidationResult.Valid].
 *
 * 2. If `this` list contains only [ValidationResult.Valid], returns [ValidationResult.Valid].
 *
 * 3. If `this` contains at least one [ValidationResult.Invalid],
 * flattens all [violations][ValidationResult.Invalid.violations] found in `this` list
 * and returns a new [ValidationResult.Invalid] with the flattened violations.
 *
 * ### Example:
 * ```kt
 * val invalid = ValidationResult.Invalid(violation1, violation2)
 * val invalid2 = ValidationResult.Invalid(violation3, violation4)
 * val valid = ValidationResult.Valid
 *
 * // ValidationResult.Valid
 * emptyList<ValidationResult>().mergeValidationResults()
 *
 * // ValidationResult.Valid
 * listOf(valid, valid).mergeValidationResults()
 *
 * // ValidationResult.Invalid(violation1, violation2, violation3, violation4)
 * listOf(invalid, valid, invalid2).mergeValidationResults()
 * ```
 *
 * @return The merged [ValidationResult]
 */
public fun List<ValidationResult>.mergeValidationResults(): ValidationResult =
    ValidationResult(
        violations = this.getViolations(),
    )

/**
 * Returns a flattened list of all [violations][ValidationResult.Invalid.violations] from `this` list.
 *
 * Uses [buildList] to filter and collect violations from each [ValidationResult.Invalid].
 */
@Suppress("NOTHING_TO_INLINE")
private inline fun List<ValidationResult>.getViolations(): List<Violation> =
    if (this.isEmpty()) {
        emptyList()
    } else {
        buildList {
            for (result in this@getViolations) {
                if (result is ValidationResult.Invalid) addAll(result.violations)
            }
        }
    }

/**
 * Executes [block] if `this` result is [ValidationResult.Valid].
 *
 * ### Example:
 * ```kt
 * val valid = ValidationResult.Valid
 * val invalid = ValidationResult.Invalid(violation)
 *
 * valid.ifValid { "Success!" } // "Success!"
 * invalid.ifValid { "Success!" } // null
 * ```
 *
 * @param block The block to execute
 * @return The result of [block], or `null` if `this` is [ValidationResult.Invalid]
 */
public inline fun <T> ValidationResult.ifValid(block: () -> T): T? =
    if (this.isValid) {
        block()
    } else {
        null
    }

/**
 * Executes [block] if `this` result is [ValidationResult.Invalid].
 *
 * ### Example:
 * ```kt
 * val valid = ValidationResult.Valid
 * val invalid = ValidationResult.Invalid(violation1, violation2)
 *
 * valid.ifInvalid { violations -> violations.size } // null
 * invalid.ifInvalid { violations -> violations.size } // 2
 * ```
 *
 * @param block The block to execute with the list of [violations][ValidationResult.Invalid.violations]
 * @return The result of [block], or `null` if `this` is [ValidationResult.Valid]
 */
public inline fun <T> ValidationResult.ifInvalid(block: (List<Violation>) -> T): T? =
    if (this is ValidationResult.Invalid) {
        block(this.violations)
    } else {
        null
    }

/**
 * Applies [ifValid] if `this` is [ValidationResult.Valid], or [ifInvalid] if `this` is [ValidationResult.Invalid].
 *
 * ### Example:
 * ```kt
 * val valid = ValidationResult.Valid
 * val invalid = ValidationResult.Invalid(violation1, violation2)
 *
 * valid.fold(
 *     ifValid = { "Success!" },
 *     ifInvalid = { violations -> "Failed with ${violations.size} errors" }
 * ) // "Success!"
 *
 * invalid.fold(
 *     ifValid = { "Success!" },
 *     ifInvalid = { violations -> "Failed with ${violations.size} errors" }
 * ) // "Failed with 2 errors"
 * ```
 *
 * @param ifValid The block to execute if the result is valid
 * @param ifInvalid The block to execute if the result is invalid
 * @return The result of the executed block
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
 * Executes [block] if `this` result is [ValidationResult.Valid], then returns `this`.
 *
 * Useful for side effects like logging or notifications while maintaining fluent chaining.
 *
 * ### Example:
 * ```kt
 * val result = ValidationResult.Valid
 *     .onValid { println("Validation passed!") }
 *     .onInvalid { violations -> println("Failed: $violations") }
 *
 * // Prints: "Validation passed!"
 * // result: ValidationResult.Valid
 * ```
 *
 * @param block The block to execute
 * @return `this` [ValidationResult]
 */
public inline fun ValidationResult.onValid(block: () -> Unit): ValidationResult =
    apply {
        if (this.isValid) block()
    }

/**
 * Executes [block] if `this` result is [ValidationResult.Invalid], then returns `this`.
 *
 * Useful for side effects like logging or error reporting while maintaining fluent chaining.
 *
 * ### Example:
 * ```kt
 * val result = ValidationResult.Invalid(violation1, violation2)
 *     .onValid { println("Validation passed!") }
 *     .onInvalid { violations -> println("Failed with ${violations.size} errors") }
 *
 * // Prints: "Failed with 2 errors"
 * // result: ValidationResult.Invalid(violation1, violation2)
 * ```
 *
 * @param block The block to execute with the list of [violations][ValidationResult.Invalid.violations]
 * @return This [ValidationResult]
 */
public inline fun ValidationResult.onInvalid(block: (List<Violation>) -> Unit): ValidationResult =
    apply {
        if (this is ValidationResult.Invalid) block(this.violations)
    }

/**
 * Throws a [ValidationException] if `this` result is [ValidationResult.Invalid].
 *
 * The exception message is constructed by joining the [violations][ValidationResult.Invalid.violations]
 * using the provided formatting parameters.
 *
 * @param separator The separator between violations
 * @param prefix The prefix to add before the violations list if there are any violations present
 * @param postfix The postfix to add after the violations list
 * @param limit The maximum number of violations to include, or -1 for unlimited
 * @param truncated The text to show when violations are truncated
 * @param prefixOnEmpty The prefix to use when the violations list is empty
 * @param cause The optional cause of the exception
 * @param transform The function to transform each violation into a string
 * @throws ValidationException if `this` is [ValidationResult.Invalid]
 */
@Suppress("LongParameterList")
public fun ValidationResult.throwOnFailure(
    separator: CharSequence = "\n",
    prefix: CharSequence = "Validation failed:\n",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "...",
    prefixOnEmpty: CharSequence = "Validation failed",
    cause: Throwable? = null,
    transform: (Violation) -> String = { "\t- ${it.reason}" },
) {
    if (this !is ValidationResult.Invalid) return

    val prefix =
        if (violations.isEmpty()) {
            prefixOnEmpty
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
