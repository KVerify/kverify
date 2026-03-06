package io.github.kverify.rules

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.Verification
import io.github.kverify.core.scope.failIf
import io.github.kverify.violations.ExactLengthViolation
import io.github.kverify.violations.LengthRangeViolation
import io.github.kverify.violations.MaxLengthViolation
import io.github.kverify.violations.MinLengthViolation
import io.github.kverify.violations.NotBlankViolation

public fun Verification<String>.notBlank(reason: String? = null): Verification<String> =
    apply {
        scope.failIf({ value.isBlank() }) {
            NotBlankViolation(
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must not be blank",
            )
        }
    }

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
