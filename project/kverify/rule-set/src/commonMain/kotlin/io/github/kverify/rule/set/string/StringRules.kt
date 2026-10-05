package io.github.kverify.rule.set.string

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.ValueVerificationScope
import io.github.kverify.core.strategy.failIf

public fun ValueVerificationScope<String, *>.notBlank(reason: String? = null) {
    validationStrategy.failIf(value.isBlank()) {
        NotBlankViolation(
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must not be blank",
        )
    }
}

public fun ValueVerificationScope<String, *>.minLength(
    min: Int,
    reason: String? = null,
) {
    val actualLength = value.length
    validationStrategy.failIf(actualLength < min) {
        MinLengthViolation(
            minLengthAllowed = min,
            actualLength = actualLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be at least $min characters long. Actual length: $actualLength",
        )
    }
}

public fun ValueVerificationScope<String, *>.maxLength(
    max: Int,
    reason: String? = null,
) {
    val actualLength = value.length
    validationStrategy.failIf(actualLength > max) {
        MaxLengthViolation(
            maxLengthAllowed = max,
            actualLength = actualLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be at most $max characters long. Actual length: $actualLength",
        )
    }
}

public fun ValueVerificationScope<String, *>.exactLength(
    length: Int,
    reason: String? = null,
) {
    val actualLength = value.length
    validationStrategy.failIf(actualLength != length) {
        ExactLengthViolation(
            expectedLength = length,
            actualLength = actualLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be exactly $length characters long. Actual length: $actualLength",
        )
    }
}

public fun ValueVerificationScope<String, *>.matches(
    pattern: Regex,
    reason: String? = null,
) {
    val actualValue = value
    validationStrategy.failIf(!pattern.matches(actualValue)) {
        PatternViolation(
            pattern = pattern.pattern,
            actualValue = actualValue,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must match pattern \"${pattern.pattern}\". Actual value: \"$actualValue\"",
        )
    }
}

public fun ValueVerificationScope<String, *>.lengthRange(
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
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be between $min and $max characters long. Actual length: $actualLength",
        )
    }
}
