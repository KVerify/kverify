package io.github.kverify.rule.set.string

import io.github.kverify.core.model.toList
import io.github.kverify.core.strategy.failIf
import io.github.kverify.core.verification.ValueVerification

public fun ValueVerification<String, *>.notBlank(reason: String? = null) {
    validationStrategy.failIf(value.isBlank()) {
        NotBlankViolation(
            validationPath = validationPath.toList(),
            reason = reason ?: "Value must not be blank",
        )
    }
}

public fun ValueVerification<String, *>.minLength(
    min: Int,
    reason: String? = null,
) {
    val actualLength = value.length
    validationStrategy.failIf(actualLength < min) {
        MinLengthViolation(
            minLengthAllowed = min,
            actualLength = actualLength,
            validationPath = validationPath.toList(),
            reason = reason ?: "Value must be at least $min characters long. Actual length: $actualLength",
        )
    }
}

public fun ValueVerification<String, *>.maxLength(
    max: Int,
    reason: String? = null,
) {
    val actualLength = value.length
    validationStrategy.failIf(actualLength > max) {
        MaxLengthViolation(
            maxLengthAllowed = max,
            actualLength = actualLength,
            validationPath = validationPath.toList(),
            reason = reason ?: "Value must be at most $max characters long. Actual length: $actualLength",
        )
    }
}

public fun ValueVerification<String, *>.exactLength(
    length: Int,
    reason: String? = null,
) {
    val actualLength = value.length
    validationStrategy.failIf(actualLength != length) {
        ExactLengthViolation(
            expectedLength = length,
            actualLength = actualLength,
            validationPath = validationPath.toList(),
            reason = reason ?: "Value must be exactly $length characters long. Actual length: $actualLength",
        )
    }
}

public fun ValueVerification<String, *>.matches(
    pattern: Regex,
    reason: String? = null,
) {
    val actualValue = value
    validationStrategy.failIf(!pattern.matches(actualValue)) {
        PatternViolation(
            pattern = pattern.pattern,
            actualValue = actualValue,
            validationPath = validationPath.toList(),
            reason = reason ?: "Value must match pattern \"${pattern.pattern}\". Actual value: \"$actualValue\"",
        )
    }
}

public fun ValueVerification<String, *>.lengthRange(
    min: Int,
    max: Int,
    reason: String? = null,
) {
    val actualLength = value.length
    validationStrategy.failIf(actualLength !in min..max) {
        LengthRangeViolation(
            minLengthAllowed = min,
            maxLengthAllowed = max,
            actualLength = actualLength,
            validationPath = validationPath.toList(),
            reason = reason ?: "Value must be between $min and $max characters long. Actual length: $actualLength",
        )
    }
}
