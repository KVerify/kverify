package io.github.kverify.rules

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validationPath
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.scope.Verification
import io.github.kverify.core.violation.Violation
import io.github.kverify.violations.ExactLengthViolation
import io.github.kverify.violations.LengthRangeViolation
import io.github.kverify.violations.MaxLengthViolation
import io.github.kverify.violations.MinLengthViolation
import io.github.kverify.violations.NotBlankViolation
import io.github.kverify.violations.PatternViolation

public fun Verification<String>.notBlank(reason: String? = null): Verification<String> =
    apply {
        val rule = StringNotBlankRule(value, scope.validationContext, reason)
        scope.enforce(rule)
    }

public fun Verification<String>.minLength(
    min: Int,
    reason: String? = null,
): Verification<String> =
    apply {
        val rule = StringMinLengthRule(value, scope.validationContext, min, reason)
        scope.enforce(rule)
    }

public fun Verification<String>.maxLength(
    max: Int,
    reason: String? = null,
): Verification<String> =
    apply {
        val rule = StringMaxLengthRule(value, scope.validationContext, max, reason)
        scope.enforce(rule)
    }

public fun Verification<String>.exactLength(
    length: Int,
    reason: String? = null,
): Verification<String> =
    apply {
        val rule = StringExactLengthRule(value, scope.validationContext, length, reason)
        scope.enforce(rule)
    }

public fun Verification<String>.lengthRange(
    min: Int,
    max: Int,
    reason: String? = null,
): Verification<String> =
    apply {
        val rule = StringLengthRangeRule(value, scope.validationContext, min, max, reason)
        scope.enforce(rule)
    }

public fun Verification<String>.pattern(
    regex: Regex,
    reason: String? = null,
): Verification<String> =
    apply {
        val rule = StringPatternRule(value, scope.validationContext, regex, reason)
        scope.enforce(rule)
    }

private class StringNotBlankRule(
    private val value: String,
    private val context: ValidationContext,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? =
        if (value.isBlank()) {
            NotBlankViolation(
                validationPath = context.validationPath(),
                reason = reason ?: "Value must not be blank",
            )
        } else {
            null
        }
}

private class StringMinLengthRule(
    private val value: String,
    private val context: ValidationContext,
    private val minLength: Int,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? {
        val actualLength = value.length

        return if (actualLength < minLength) {
            MinLengthViolation(
                minLengthAllowed = minLength,
                actualLength = actualLength,
                validationPath = context.validationPath(),
                reason =
                    reason
                        ?: "Value must be at least $minLength characters long. Actual length: $actualLength",
            )
        } else {
            null
        }
    }
}

private class StringMaxLengthRule(
    private val value: String,
    private val context: ValidationContext,
    private val maxLength: Int,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? {
        val actualLength = value.length

        return if (actualLength > maxLength) {
            MaxLengthViolation(
                maxLengthAllowed = maxLength,
                actualLength = actualLength,
                validationPath = context.validationPath(),
                reason =
                    reason
                        ?: "Value must be at most $maxLength characters long. Actual length: $actualLength",
            )
        } else {
            null
        }
    }
}

private class StringExactLengthRule(
    private val value: String,
    private val context: ValidationContext,
    private val expectedLength: Int,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? {
        val actualLength = value.length

        return if (actualLength != expectedLength) {
            ExactLengthViolation(
                expectedLength = expectedLength,
                actualLength = actualLength,
                validationPath = context.validationPath(),
                reason =
                    reason
                        ?: "Value must be exactly $expectedLength characters long. Actual length: $actualLength",
            )
        } else {
            null
        }
    }
}

private class StringLengthRangeRule(
    private val value: String,
    private val context: ValidationContext,
    private val minLength: Int,
    private val maxLength: Int,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? {
        val actualLength = value.length

        return if (actualLength !in minLength..maxLength) {
            LengthRangeViolation(
                minLengthAllowed = minLength,
                maxLengthAllowed = maxLength,
                actualLength = actualLength,
                validationPath = context.validationPath(),
                reason =
                    reason
                        ?: "Value must be between $minLength and $maxLength characters long. Actual length: $actualLength",
            )
        } else {
            null
        }
    }
}

private class StringPatternRule(
    private val value: String,
    private val context: ValidationContext,
    private val regex: Regex,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? =
        if (!value.matches(regex)) {
            PatternViolation(
                regex = regex,
                validationPath = context.validationPath(),
                reason =
                    reason
                        ?: "Value must match the pattern: $regex",
            )
        } else {
            null
        }
}
