package io.github.kverify.rules

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.scope.Verification
import io.github.kverify.core.violation.Violation
import io.github.kverify.violations.AtLeastViolation
import io.github.kverify.violations.AtMostViolation
import io.github.kverify.violations.BetweenViolation
import io.github.kverify.violations.GreaterThanViolation
import io.github.kverify.violations.LessThanViolation

public fun <T : Comparable<T>> Verification<T>.atLeast(
    min: T,
    reason: String? = null,
): Verification<T> =
    apply {
        val rule = ComparableAtLeastRule(value, scope.validationContext, min, reason)
        scope.enforce(rule)
    }

public fun <T : Comparable<T>> Verification<T>.atMost(
    max: T,
    reason: String? = null,
): Verification<T> =
    apply {
        val rule = ComparableAtMostRule(value, scope.validationContext, max, reason)
        scope.enforce(rule)
    }

public fun <T : Comparable<T>> Verification<T>.between(
    min: T,
    max: T,
    reason: String? = null,
): Verification<T> =
    apply {
        val rule = ComparableBetweenRule(value, scope.validationContext, min, max, reason)
        scope.enforce(rule)
    }

public fun <T : Comparable<T>> Verification<T>.greaterThan(
    min: T,
    reason: String? = null,
): Verification<T> =
    apply {
        val rule = ComparableGreaterThanRule(value, scope.validationContext, min, reason)
        scope.enforce(rule)
    }

public fun <T : Comparable<T>> Verification<T>.lessThan(
    max: T,
    reason: String? = null,
): Verification<T> =
    apply {
        val rule = ComparableLessThanRule(value, scope.validationContext, max, reason)
        scope.enforce(rule)
    }

private class ComparableAtLeastRule<T : Comparable<T>>(
    private val value: T,
    private val context: ValidationContext,
    private val min: T,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? =
        if (value < min) {
            AtLeastViolation(
                minAllowed = min,
                actual = value,
                validationPath = context.validationPath(),
                reason = reason ?: "Value must be at least $min. Actual: $value",
            )
        } else {
            null
        }
}

private class ComparableAtMostRule<T : Comparable<T>>(
    private val value: T,
    private val context: ValidationContext,
    private val max: T,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? =
        if (value > max) {
            AtMostViolation(
                maxAllowed = max,
                actual = value,
                validationPath = context.validationPath(),
                reason = reason ?: "Value must be at most $max. Actual: $value",
            )
        } else {
            null
        }
}

private class ComparableBetweenRule<T : Comparable<T>>(
    private val value: T,
    private val context: ValidationContext,
    private val min: T,
    private val max: T,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? =
        if (value !in min..max) {
            BetweenViolation(
                min = min,
                max = max,
                actual = value,
                validationPath = context.validationPath(),
                reason = reason ?: "Value must be between $min and $max. Actual: $value",
            )
        } else {
            null
        }
}

private class ComparableGreaterThanRule<T : Comparable<T>>(
    private val value: T,
    private val context: ValidationContext,
    private val minExclusive: T,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? =
        if (value <= minExclusive) {
            GreaterThanViolation(
                minExclusive = minExclusive,
                actual = value,
                validationPath = context.validationPath(),
                reason = reason ?: "Value must be greater than $minExclusive. Actual: $value",
            )
        } else {
            null
        }
}

private class ComparableLessThanRule<T : Comparable<T>>(
    private val value: T,
    private val context: ValidationContext,
    private val maxExclusive: T,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? =
        if (value >= maxExclusive) {
            LessThanViolation(
                maxExclusive = maxExclusive,
                actual = value,
                validationPath = context.validationPath(),
                reason = reason ?: "Value must be less than $maxExclusive. Actual: $value",
            )
        } else {
            null
        }
}
