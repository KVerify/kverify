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
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be at least $min",
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
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be at most $max",
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
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be between $min and $max",
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
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be greater than $minExclusive",
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
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be less than $maxExclusive",
        )
    }
}
