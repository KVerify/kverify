package io.github.kverify.rules

import io.github.kverify.core.scope.ScopedVerification
import io.github.kverify.core.scope.failIf
import io.github.kverify.violations.ExactLengthViolation
import io.github.kverify.violations.LengthRangeViolation
import io.github.kverify.violations.MaxLengthViolation
import io.github.kverify.violations.MinLengthViolation
import io.github.kverify.violations.NotBlankViolation
import io.github.kverify.violations.PatternViolation

private typealias StringScopedVerification = ScopedVerification<String>

public fun StringScopedVerification.notBlank(reason: String? = null): StringScopedVerification =
    apply {
        scope.failIf(value.isBlank()) {
            NotBlankViolation(
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must not be blank",
            )
        }
    }

public fun StringScopedVerification.minLength(
    min: Int,
    reason: String? = null,
): StringScopedVerification =
    apply {
        val length = value.length
        scope.failIf(length < min) {
            MinLengthViolation(
                minLengthAllowed = min,
                actualLength = length,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be at least $min characters long. Actual length: $length",
            )
        }
    }

public fun StringScopedVerification.maxLength(
    max: Int,
    reason: String? = null,
): StringScopedVerification =
    apply {
        val length = value.length
        scope.failIf(length > max) {
            MaxLengthViolation(
                maxLengthAllowed = max,
                actualLength = length,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be at most $max characters long. Actual length: $length",
            )
        }
    }

public fun StringScopedVerification.exactLength(
    length: Int,
    reason: String? = null,
): StringScopedVerification =
    apply {
        val actualLength = value.length
        scope.failIf(actualLength != length) {
            ExactLengthViolation(
                expectedLength = length,
                actualLength = actualLength,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be exactly $length characters long. Actual length: $actualLength",
            )
        }
    }

public fun StringScopedVerification.lengthRange(
    min: Int,
    max: Int,
    reason: String? = null,
): StringScopedVerification =
    apply {
        val length = value.length
        scope.failIf(length !in min..max) {
            LengthRangeViolation(
                minLengthAllowed = min,
                maxLengthAllowed = max,
                actualLength = length,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be between $min and $max characters long. Actual length: $length",
            )
        }
    }

public fun StringScopedVerification.pattern(
    regex: Regex,
    reason: String? = null,
): StringScopedVerification =
    apply {
        scope.failIf(!value.matches(regex)) {
            PatternViolation(
                regex = regex,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must match the pattern: $regex",
            )
        }
    }
