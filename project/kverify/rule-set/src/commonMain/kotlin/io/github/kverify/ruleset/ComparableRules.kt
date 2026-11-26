package io.github.kverify.ruleset

import io.github.kverify.core.ValidationPath
import io.github.kverify.core.ValueValidationScope
import io.github.kverify.core.ViolationWithPath
import io.github.kverify.core.failIf
import io.github.kverify.core.validationPath

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

public class AtLeastViolation<T : Comparable<T>>(
    public val min: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "AtLeastViolation(min=$min, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is AtLeastViolation<*> -> {
                false
            }

            else -> {
                min == other.min &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = min.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}

public class AtMostViolation<T : Comparable<T>>(
    public val max: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "AtMostViolation(max=$max, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is AtMostViolation<*> -> {
                false
            }

            else -> {
                max == other.max &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = max.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}

public class GreaterThanViolation<T : Comparable<T>>(
    public val minExclusive: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "GreaterThanViolation(minExclusive=$minExclusive, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is GreaterThanViolation<*> -> {
                false
            }

            else -> {
                minExclusive == other.minExclusive &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = minExclusive.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}

public class LessThanViolation<T : Comparable<T>>(
    public val maxExclusive: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "LessThanViolation(maxExclusive=$maxExclusive, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is LessThanViolation<*> -> {
                false
            }

            else -> {
                maxExclusive == other.maxExclusive &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = maxExclusive.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}

public class BetweenViolation<T : Comparable<T>>(
    public val min: T,
    public val max: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "BetweenViolation(min=$min, max=$max, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is BetweenViolation<*> -> {
                false
            }

            else -> {
                min == other.min &&
                    max == other.max &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = min.hashCode()
        result = 31 * result + max.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
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
