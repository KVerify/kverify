package io.github.kverify.rule.set.string

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.ValueValidationScope
import io.github.kverify.core.scope.failIf

public fun ValueValidationScope<String, *>.notBlank(reason: String? = null) {
    failIf(value.isBlank()) {
        NotBlankViolation(
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must not be blank",
        )
    }
}

public fun ValueValidationScope<String, *>.minLength(
    min: Int,
    reason: String? = null,
) {
    val actualLength = value.length

    failIf(actualLength < min) {
        MinLengthViolation(
            minLengthAllowed = min,
            actualLength = actualLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be at least $min characters long. Actual length: $actualLength",
        )
    }
}

public fun ValueValidationScope<String, *>.maxLength(
    max: Int,
    reason: String? = null,
) {
    val actualLength = value.length

    failIf(actualLength > max) {
        MaxLengthViolation(
            maxLengthAllowed = max,
            actualLength = actualLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be at most $max characters long. Actual length: $actualLength",
        )
    }
}

public fun ValueValidationScope<String, *>.exactLength(
    length: Int,
    reason: String? = null,
) {
    val actualLength = value.length

    failIf(actualLength != length) {
        ExactLengthViolation(
            expectedLength = length,
            actualLength = actualLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be exactly $length characters long. Actual length: $actualLength",
        )
    }
}

public fun ValueValidationScope<String, *>.matches(
    pattern: Regex,
    reason: String? = null,
) {
    failIf(!pattern.matches(value)) {
        PatternViolation(
            pattern = pattern.pattern,
            actualValue = value,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must match pattern \"${pattern.pattern}\". Actual value: \"$value\"",
        )
    }
}

public fun ValueValidationScope<String, *>.lengthRange(
    min: Int,
    max: Int,
    reason: String? = null,
) {
    val actualLength = value.length

    failIf(actualLength !in min..max) {
        LengthRangeViolation(
            minLengthAllowed = min,
            maxLengthAllowed = max,
            actualLength = actualLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be between $min and $max characters long. Actual length: $actualLength",
        )
    }
}
