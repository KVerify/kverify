package io.github.kverify.rules

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.failIf
import io.github.kverify.core.verification.Verification
import io.github.kverify.violations.ExactLengthViolation
import io.github.kverify.violations.LengthRangeViolation
import io.github.kverify.violations.MaxLengthViolation
import io.github.kverify.violations.MinLengthViolation
import io.github.kverify.violations.NotBlankViolation

public fun <V : Verification<String>> V.notBlank(reason: String? = null): V =
    apply {
        scope.failIf({ value.isBlank() }) {
            NotBlankViolation(
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must not be blank",
            )
        }
    }

public fun <V : Verification<String>> V.minLength(
    min: Int,
    reason: String? = null,
): V =
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

public fun <V : Verification<String>> V.maxLength(
    max: Int,
    reason: String? = null,
): V =
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

public fun <V : Verification<String>> V.exactLength(
    length: Int,
    reason: String? = null,
): V =
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

public fun <V : Verification<String>> V.lengthRange(
    min: Int,
    max: Int,
    reason: String? = null,
): V =
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
