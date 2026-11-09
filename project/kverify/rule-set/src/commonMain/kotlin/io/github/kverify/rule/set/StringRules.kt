package io.github.kverify.rule.set

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.ValueValidationScope
import io.github.kverify.core.scope.failIf
import io.github.kverify.rule.set.violation.ExactLengthViolation
import io.github.kverify.rule.set.violation.LengthRangeViolation
import io.github.kverify.rule.set.violation.MaxLengthViolation
import io.github.kverify.rule.set.violation.MinLengthViolation
import io.github.kverify.rule.set.violation.NotBlankViolation
import io.github.kverify.rule.set.violation.PatternViolation

/**
 * Reports a [NotBlankViolation] if the value is blank.
 *
 * @param reason the reason of the reported violation, or `null` to use "Must not be blank".
 */
public fun ValueValidationScope<CharSequence>.notBlank(reason: String? = null) {
    failIf(value.isBlank()) {
        NotBlankViolation(
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must not be blank",
        )
    }
}

/**
 * Reports a [MinLengthViolation] if the length of the value is less than [minLength].
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Length must be at least [minLength]".
 */
public fun ValueValidationScope<CharSequence>.minLength(
    minLength: Int,
    reason: String? = null,
) {
    failIf(value.length < minLength) {
        MinLengthViolation(
            minLength = minLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Length must be at least $minLength",
        )
    }
}

/**
 * Reports a [MaxLengthViolation] if the length of the value is greater than [maxLength].
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Length must be at most [maxLength]".
 */
public fun ValueValidationScope<CharSequence>.maxLength(
    maxLength: Int,
    reason: String? = null,
) {
    failIf(value.length > maxLength) {
        MaxLengthViolation(
            maxLength = maxLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Length must be at most $maxLength",
        )
    }
}

/**
 * Reports a [LengthRangeViolation] if the length of the value is not between [minLength]
 * and [maxLength], inclusive.
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Length must be between [minLength] and [maxLength]".
 */
public fun ValueValidationScope<CharSequence>.lengthRange(
    minLength: Int,
    maxLength: Int,
    reason: String? = null,
) {
    failIf(value.length !in minLength..maxLength) {
        LengthRangeViolation(
            minLength = minLength,
            maxLength = maxLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Length must be between $minLength and $maxLength",
        )
    }
}

/**
 * Reports an [ExactLengthViolation] if the length of the value is not [expectedLength].
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Length must be exactly [expectedLength]".
 */
public fun ValueValidationScope<CharSequence>.exactLength(
    expectedLength: Int,
    reason: String? = null,
) {
    failIf(value.length != expectedLength) {
        ExactLengthViolation(
            expectedLength = expectedLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Length must be exactly $expectedLength",
        )
    }
}

/**
 * Reports a [PatternViolation] if the value does not match [regex] entirely.
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Must match pattern" followed by the pattern of [regex].
 */
public fun ValueValidationScope<CharSequence>.matches(
    regex: Regex,
    reason: String? = null,
) {
    failIf(!regex.matches(value)) {
        PatternViolation(
            regex = regex,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must match pattern ${regex.pattern}",
        )
    }
}
