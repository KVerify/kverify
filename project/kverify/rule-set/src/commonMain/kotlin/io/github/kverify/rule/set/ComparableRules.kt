package io.github.kverify.rule.set

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.ValueValidationScope
import io.github.kverify.core.scope.failIf
import io.github.kverify.rule.set.violation.AtLeastViolation
import io.github.kverify.rule.set.violation.AtMostViolation
import io.github.kverify.rule.set.violation.BetweenViolation
import io.github.kverify.rule.set.violation.GreaterThanViolation
import io.github.kverify.rule.set.violation.LessThanViolation

/**
 * Reports an [AtLeastViolation] if the value is not at least [min].
 *
 * @param reason the reason of the reported violation, or `null` to use "Must be at least [min]".
 */
public fun <T : Comparable<T>> ValueValidationScope<T>.atLeast(
    min: T,
    reason: String? = null,
) {
    failIf(!isAtLeast(value, min)) {
        AtLeastViolation(
            min = min,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must be at least $min",
        )
    }
}

/**
 * Reports an [AtMostViolation] if the value is not at most [max].
 *
 * @param reason the reason of the reported violation, or `null` to use "Must be at most [max]".
 */
public fun <T : Comparable<T>> ValueValidationScope<T>.atMost(
    max: T,
    reason: String? = null,
) {
    failIf(!isAtMost(value, max)) {
        AtMostViolation(
            max = max,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must be at most $max",
        )
    }
}

/**
 * Reports a [GreaterThanViolation] if the value is not greater than [minExclusive].
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Must be greater than [minExclusive]".
 */
public fun <T : Comparable<T>> ValueValidationScope<T>.greaterThan(
    minExclusive: T,
    reason: String? = null,
) {
    failIf(!isGreaterThan(value, minExclusive)) {
        GreaterThanViolation(
            minExclusive = minExclusive,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must be greater than $minExclusive",
        )
    }
}

/**
 * Reports a [LessThanViolation] if the value is not less than [maxExclusive].
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Must be less than [maxExclusive]".
 */
public fun <T : Comparable<T>> ValueValidationScope<T>.lessThan(
    maxExclusive: T,
    reason: String? = null,
) {
    failIf(!isLessThan(value, maxExclusive)) {
        LessThanViolation(
            maxExclusive = maxExclusive,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must be less than $maxExclusive",
        )
    }
}

/**
 * Reports a [BetweenViolation] if the value is not between [min] and [max], inclusive.
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Must be between [min] and [max]".
 */
public fun <T : Comparable<T>> ValueValidationScope<T>.between(
    min: T,
    max: T,
    reason: String? = null,
) {
    failIf(!isBetween(value, min, max)) {
        BetweenViolation(
            min = min,
            max = max,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must be between $min and $max",
        )
    }
}

// The rules report a violation when a check does NOT hold, so a NaN value, for which every
// comparison is false, is always reported.
//
// Double and Float are compared by IEEE 754 rules, where -0.0 equals 0.0. Generic code compares
// with compareTo instead, which puts NaN above everything and -0.0 below 0.0.
private fun <T : Comparable<T>> isAtLeast(
    value: T,
    min: T,
): Boolean =
    when (value) {
        is Double if min is Double -> ieeeAtLeast(value, min)
        is Float if min is Float -> ieeeAtLeast(value.toDouble(), min.toDouble())
        else -> value >= min
    }

private fun <T : Comparable<T>> isAtMost(
    value: T,
    max: T,
): Boolean =
    when (value) {
        is Double if max is Double -> ieeeAtMost(value, max)
        is Float if max is Float -> ieeeAtMost(value.toDouble(), max.toDouble())
        else -> value <= max
    }

private fun <T : Comparable<T>> isGreaterThan(
    value: T,
    minExclusive: T,
): Boolean =
    when (value) {
        is Double if minExclusive is Double -> ieeeGreaterThan(value, minExclusive)
        is Float if minExclusive is Float -> ieeeGreaterThan(value.toDouble(), minExclusive.toDouble())
        else -> value > minExclusive
    }

private fun <T : Comparable<T>> isLessThan(
    value: T,
    maxExclusive: T,
): Boolean =
    when (value) {
        is Double if maxExclusive is Double -> ieeeLessThan(value, maxExclusive)
        is Float if maxExclusive is Float -> ieeeLessThan(value.toDouble(), maxExclusive.toDouble())
        else -> value < maxExclusive
    }

private fun <T : Comparable<T>> isBetween(
    value: T,
    min: T,
    max: T,
): Boolean = isAtLeast(value, min) && isAtMost(value, max)

// The parameters are declared as Double, so the compiler uses IEEE 754 comparison here.
// Inside the generic functions above it would use compareTo, even after a smart cast.
// Float values are widened to Double first, which is exact and keeps NaN, -0.0 and infinity.
private fun ieeeAtLeast(
    value: Double,
    min: Double,
): Boolean = value >= min

private fun ieeeAtMost(
    value: Double,
    max: Double,
): Boolean = value <= max

private fun ieeeGreaterThan(
    value: Double,
    minExclusive: Double,
): Boolean = value > minExclusive

private fun ieeeLessThan(
    value: Double,
    maxExclusive: Double,
): Boolean = value < maxExclusive