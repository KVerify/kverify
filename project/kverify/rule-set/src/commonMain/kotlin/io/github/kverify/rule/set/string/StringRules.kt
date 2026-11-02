package io.github.kverify.rule.set.string

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.ValueValidationScope
import io.github.kverify.core.scope.failIf

public fun ValueValidationScope<String>.notBlank(reason: String? = null) {
    failIf(value.isBlank()) {
        NotBlankViolation(
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must not be blank",
        )
    }
}

public fun ValueValidationScope<String>.minLength(
    minLengthAllowed: Int,
    reason: String? = null,
) {
    val actualLength = value.length

    failIf(actualLength < minLengthAllowed) {
        MinLengthViolation(
            minLengthAllowed = minLengthAllowed,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be at least $minLengthAllowed characters long",
        )
    }
}

public fun ValueValidationScope<String>.maxLength(
    maxLengthAllowed: Int,
    reason: String? = null,
) {
    val actualLength = value.length

    failIf(actualLength > maxLengthAllowed) {
        MaxLengthViolation(
            maxLengthAllowed = maxLengthAllowed,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be at most $maxLengthAllowed characters long",
        )
    }
}

public fun ValueValidationScope<String>.exactLength(
    length: Int,
    reason: String? = null,
) {
    val actualLength = value.length

    failIf(actualLength != length) {
        ExactLengthViolation(
            expectedLength = length,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be exactly $length characters long",
        )
    }
}

public fun ValueValidationScope<String>.matches(
    pattern: Regex,
    reason: String? = null,
) {
    failIf(!pattern.matches(value)) {
        PatternViolation(
            pattern = pattern.pattern,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must match pattern \"${pattern.pattern}\"",
        )
    }
}

public fun ValueValidationScope<String>.lengthRange(
    minLengthAllowed: Int,
    maxLengthAllowed: Int,
    reason: String? = null,
) {
    val actualLength = value.length

    failIf(actualLength !in minLengthAllowed..maxLengthAllowed) {
        LengthRangeViolation(
            minLengthAllowed = minLengthAllowed,
            maxLengthAllowed = maxLengthAllowed,
            validationPath = validationContext.validationPath(),
            reason =
                reason
                    ?: "Value must be between $minLengthAllowed and $maxLengthAllowed characters long",
        )
    }
}
