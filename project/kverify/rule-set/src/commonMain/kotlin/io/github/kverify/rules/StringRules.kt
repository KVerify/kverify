package io.github.kverify.rules

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.Verification
import io.github.kverify.core.scope.failIf
import io.github.kverify.violations.ExactLengthViolation
import io.github.kverify.violations.LengthRangeViolation
import io.github.kverify.violations.MaxLengthViolation
import io.github.kverify.violations.MinLengthViolation
import io.github.kverify.violations.NotBlankViolation

/**
 * Fails with [NotBlankViolation] if the string is blank.
 *
 * If [reason] is `null`, defaults to: `"Value must not be blank"`.
 */
public fun Verification<String>.notBlank(reason: String? = null): Verification<String> =
    apply {
        scope.failIf({ value.isBlank() }) {
            NotBlankViolation(
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must not be blank",
            )
        }
    }

/**
 * Fails with [MinLengthViolation] if the string is shorter than [min] characters (inclusive).
 *
 * If [reason] is `null`, defaults to: `"Value must be at least $min characters long. Actual length: $actualLength"`.
 */
public fun Verification<String>.minLength(
    min: Int,
    reason: String? = null,
): Verification<String> =
    apply {
        val actualLength = value.length
        scope.failIf({ actualLength < min }) {
            MinLengthViolation(
                minLengthAllowed = min,
                actualLength = actualLength,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be at least $min characters long. Actual length: $actualLength",
            )
        }
    }

/**
 * Fails with [MaxLengthViolation] if the string is longer than [max] characters (inclusive).
 *
 * If [reason] is `null`, defaults to: `"Value must be at most $max characters long. Actual length: $actualLength"`.
 */
public fun Verification<String>.maxLength(
    max: Int,
    reason: String? = null,
): Verification<String> =
    apply {
        val actualLength = value.length
        scope.failIf({ actualLength > max }) {
            MaxLengthViolation(
                maxLengthAllowed = max,
                actualLength = actualLength,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be at most $max characters long. Actual length: $actualLength",
            )
        }
    }

/**
 * Fails with [ExactLengthViolation] if the string length does not equal [length].
 *
 * If [reason] is `null`, defaults to: `"Value must be exactly $length characters long. Actual length: $actualLength"`.
 */
public fun Verification<String>.exactLength(
    length: Int,
    reason: String? = null,
): Verification<String> =
    apply {
        val actualLength = value.length
        scope.failIf({ actualLength != length }) {
            ExactLengthViolation(
                expectedLength = length,
                actualLength = actualLength,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be exactly $length characters long. Actual length: $actualLength",
            )
        }
    }

/**
 * Fails with [LengthRangeViolation] if the string length is outside the range [[min], [max]] (both inclusive).
 *
 * If [reason] is `null`, defaults to: `"Value must be between $min and $max characters long. Actual length: $actualLength"`.
 */
public fun Verification<String>.lengthRange(
    min: Int,
    max: Int,
    reason: String? = null,
): Verification<String> =
    apply {
        val actualLength = value.length
        scope.failIf({ actualLength !in min..max }) {
            LengthRangeViolation(
                minLengthAllowed = min,
                maxLengthAllowed = max,
                actualLength = actualLength,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be between $min and $max characters long. Actual length: $actualLength",
            )
        }
    }
