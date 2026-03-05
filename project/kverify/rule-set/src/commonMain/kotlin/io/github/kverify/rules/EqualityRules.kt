package io.github.kverify.rules

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validationPath
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.scope.Verification
import io.github.kverify.core.violation.Violation
import io.github.kverify.violations.EqualToViolation
import io.github.kverify.violations.NoneOfViolation
import io.github.kverify.violations.NotEqualToViolation
import io.github.kverify.violations.OneOfViolation

public fun <T, V : Verification<T>> V.equalTo(
    expected: T,
    reason: String? = null,
): V =
    apply {
        val rule = EqualityEqualToRule(value, scope.validationContext, expected, reason)
        scope.enforce(rule)
    }

public fun <T, V : Verification<T>> V.notEqualTo(
    forbidden: T,
    reason: String? = null,
): V =
    apply {
        val rule = EqualityNotEqualToRule(value, scope.validationContext, forbidden, reason)
        scope.enforce(rule)
    }

public fun <T, V : Verification<T>> V.oneOf(
    allowed: Set<T>,
    reason: String? = null,
): V =
    apply {
        val rule = EqualityOneOfRule(value, scope.validationContext, allowed, reason)
        scope.enforce(rule)
    }

public fun <T, V : Verification<T>> V.noneOf(
    forbidden: Set<T>,
    reason: String? = null,
): V =
    apply {
        val rule = EqualityNoneOfRule(value, scope.validationContext, forbidden, reason)
        scope.enforce(rule)
    }

private class EqualityEqualToRule<T>(
    private val value: T,
    private val context: ValidationContext,
    private val expected: T,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? =
        if (value != expected) {
            EqualToViolation(
                expected = expected,
                actual = value,
                validationPath = context.validationPath(),
                reason = reason ?: "Value must be equal to $expected. Actual: $value",
            )
        } else {
            null
        }
}

private class EqualityNotEqualToRule<T>(
    private val value: T,
    private val context: ValidationContext,
    private val forbidden: T,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? =
        if (value == forbidden) {
            NotEqualToViolation(
                forbidden = forbidden,
                validationPath = context.validationPath(),
                reason = reason ?: "Value must not be equal to $forbidden",
            )
        } else {
            null
        }
}

private class EqualityOneOfRule<T>(
    private val value: T,
    private val context: ValidationContext,
    private val allowed: Set<T>,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? =
        if (value !in allowed) {
            OneOfViolation(
                allowed = allowed,
                actual = value,
                validationPath = context.validationPath(),
                reason = reason ?: "Value must be one of $allowed. Actual: $value",
            )
        } else {
            null
        }
}

private class EqualityNoneOfRule<T>(
    private val value: T,
    private val context: ValidationContext,
    private val forbidden: Set<T>,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? =
        if (value in forbidden) {
            NoneOfViolation(
                forbidden = forbidden,
                actual = value,
                validationPath = context.validationPath(),
                reason = reason ?: "Value must not be one of $forbidden. Actual: $value",
            )
        } else {
            null
        }
}
