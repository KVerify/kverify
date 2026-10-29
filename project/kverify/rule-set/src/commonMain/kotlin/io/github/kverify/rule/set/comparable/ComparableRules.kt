package io.github.kverify.rule.set.comparable

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.ValueValidationScope
import io.github.kverify.core.scope.failIf

public fun <T : Comparable<T>> ValueValidationScope<T>.atLeast(
    min: T,
    reason: String? = null,
) {
    failIf(value < min) {
        AtLeastViolation(
            minAllowed = min,
            actual = value,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be at least $min. Actual: $value",
        )
    }
}

public fun <T : Comparable<T>> ValueValidationScope<T>.atMost(
    max: T,
    reason: String? = null,
) {
    failIf(value > max) {
        AtMostViolation(
            maxAllowed = max,
            actual = value,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be at most $max. Actual: $value",
        )
    }
}

public fun <T : Comparable<T>> ValueValidationScope<T>.between(
    min: T,
    max: T,
    reason: String? = null,
) {
    failIf(value !in min..max) {
        BetweenViolation(
            min = min,
            max = max,
            actual = value,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be between $min and $max. Actual: $value",
        )
    }
}

public fun <T : Comparable<T>> ValueValidationScope<T>.greaterThan(
    minExclusive: T,
    reason: String? = null,
) {
    failIf(value <= minExclusive) {
        GreaterThanViolation(
            minExclusive = minExclusive,
            actual = value,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be greater than $minExclusive. Actual: $value",
        )
    }
}

public fun <T : Comparable<T>> ValueValidationScope<T>.lessThan(
    maxExclusive: T,
    reason: String? = null,
) {
    failIf(value >= maxExclusive) {
        LessThanViolation(
            maxExclusive = maxExclusive,
            actual = value,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be less than $maxExclusive. Actual: $value",
        )
    }
}
