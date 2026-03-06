package io.github.kverify.rules

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.failIf
import io.github.kverify.core.verification.Verification
import io.github.kverify.violations.AtLeastViolation
import io.github.kverify.violations.AtMostViolation
import io.github.kverify.violations.BetweenViolation
import io.github.kverify.violations.GreaterThanViolation
import io.github.kverify.violations.LessThanViolation

public fun <T : Comparable<T>, V : Verification<T>> V.atLeast(
    min: T,
    reason: String? = null,
): V =
    apply {
        scope.failIf({ value < min }) {
            AtLeastViolation(
                minAllowed = min,
                actual = value,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be at least $min. Actual: $value",
            )
        }
    }

public fun <T : Comparable<T>, V : Verification<T>> V.atMost(
    max: T,
    reason: String? = null,
): V =
    apply {
        scope.failIf({ value > max }) {
            AtMostViolation(
                maxAllowed = max,
                actual = value,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be at most $max. Actual: $value",
            )
        }
    }

public fun <T : Comparable<T>, V : Verification<T>> V.between(
    min: T,
    max: T,
    reason: String? = null,
): V =
    apply {
        scope.failIf({ value !in min..max }) {
            BetweenViolation(
                min = min,
                max = max,
                actual = value,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be between $min and $max. Actual: $value",
            )
        }
    }

public fun <T : Comparable<T>, V : Verification<T>> V.greaterThan(
    min: T,
    reason: String? = null,
): V =
    apply {
        scope.failIf({ value <= min }) {
            GreaterThanViolation(
                minExclusive = min,
                actual = value,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be greater than $min. Actual: $value",
            )
        }
    }

public fun <T : Comparable<T>, V : Verification<T>> V.lessThan(
    max: T,
    reason: String? = null,
): V =
    apply {
        scope.failIf({ value >= max }) {
            LessThanViolation(
                maxExclusive = max,
                actual = value,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be less than $max. Actual: $value",
            )
        }
    }
