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
 * Reports an [AtLeastViolation] if the value is less than [min].
 *
 * @param reason the reason of the reported violation, or `null` to use "Must be at least [min]".
 */
public fun <T : Comparable<T>> ValueValidationScope<T>.atLeast(
    min: T,
    reason: String? = null,
) {
    failIf(value < min) {
        AtLeastViolation(
            min = min,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must be at least $min",
        )
    }
}

/**
 * Reports an [AtMostViolation] if the value is greater than [max].
 *
 * @param reason the reason of the reported violation, or `null` to use "Must be at most [max]".
 */
public fun <T : Comparable<T>> ValueValidationScope<T>.atMost(
    max: T,
    reason: String? = null,
) {
    failIf(value > max) {
        AtMostViolation(
            max = max,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must be at most $max",
        )
    }
}

/**
 * Reports a [GreaterThanViolation] if the value is less than or equal to [minExclusive].
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Must be greater than [minExclusive]".
 */
public fun <T : Comparable<T>> ValueValidationScope<T>.greaterThan(
    minExclusive: T,
    reason: String? = null,
) {
    failIf(value <= minExclusive) {
        GreaterThanViolation(
            minExclusive = minExclusive,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must be greater than $minExclusive",
        )
    }
}

/**
 * Reports a [LessThanViolation] if the value is greater than or equal to [maxExclusive].
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Must be less than [maxExclusive]".
 */
public fun <T : Comparable<T>> ValueValidationScope<T>.lessThan(
    maxExclusive: T,
    reason: String? = null,
) {
    failIf(value >= maxExclusive) {
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
    failIf((value < min) || (value > max)) {
        BetweenViolation(
            min = min,
            max = max,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must be between $min and $max",
        )
    }
}
