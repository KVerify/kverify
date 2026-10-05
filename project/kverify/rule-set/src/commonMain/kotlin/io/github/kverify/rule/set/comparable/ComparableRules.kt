package io.github.kverify.rule.set.comparable

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.ValueVerificationScope
import io.github.kverify.core.strategy.failIf

public fun <T : Comparable<T>> ValueVerificationScope<T, *>.atLeast(
    min: T,
    reason: String? = null,
) {
    validationStrategy.failIf(value < min) {
        AtLeastViolation(
            minAllowed = min,
            actual = value,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be at least $min. Actual: $value",
        )
    }
}

public fun <T : Comparable<T>> ValueVerificationScope<T, *>.atMost(
    max: T,
    reason: String? = null,
) {
    validationStrategy.failIf(value > max) {
        AtMostViolation(
            maxAllowed = max,
            actual = value,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be at most $max. Actual: $value",
        )
    }
}

public fun <T : Comparable<T>> ValueVerificationScope<T, *>.between(
    min: T,
    max: T,
    reason: String? = null,
) {
    validationStrategy.failIf(value !in min..max) {
        BetweenViolation(
            min = min,
            max = max,
            actual = value,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be between $min and $max. Actual: $value",
        )
    }
}

public fun <T : Comparable<T>> ValueVerificationScope<T, *>.greaterThan(
    min: T,
    reason: String? = null,
) {
    validationStrategy.failIf(value <= min) {
        GreaterThanViolation(
            minExclusive = min,
            actual = value,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be greater than $min. Actual: $value",
        )
    }
}

public fun <T : Comparable<T>> ValueVerificationScope<T, *>.lessThan(
    max: T,
    reason: String? = null,
) {
    validationStrategy.failIf(value >= max) {
        LessThanViolation(
            maxExclusive = max,
            actual = value,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be less than $max. Actual: $value",
        )
    }
}
