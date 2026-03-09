package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public class AtLeastViolation<T : Comparable<T>>(
    public val minAllowed: T,
    public val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AtLeastViolation<*>) return false

        return minAllowed == other.minAllowed &&
            actual == other.actual &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = minAllowed.hashCode()
        result = 31 * result + actual.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String =
        "AtLeastViolation(minAllowed=$minAllowed, actual=$actual, validationPath=$validationPath, reason=$reason)"
}

public class AtMostViolation<T : Comparable<T>>(
    public val maxAllowed: T,
    public val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AtMostViolation<*>) return false

        return maxAllowed == other.maxAllowed &&
            actual == other.actual &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = maxAllowed.hashCode()
        result = 31 * result + actual.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String =
        "AtMostViolation(maxAllowed=$maxAllowed, actual=$actual, validationPath=$validationPath, reason=$reason)"
}

public class BetweenViolation<T : Comparable<T>>(
    public val min: T,
    public val max: T,
    public val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BetweenViolation<*>) return false

        return min == other.min &&
            max == other.max &&
            actual == other.actual &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = min.hashCode()
        result = 31 * result + max.hashCode()
        result = 31 * result + actual.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String = "BetweenViolation(min=$min, max=$max, actual=$actual, validationPath=$validationPath, reason=$reason)"
}

public class GreaterThanViolation<T : Comparable<T>>(
    public val minExclusive: T,
    public val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GreaterThanViolation<*>) return false

        return minExclusive == other.minExclusive &&
            actual == other.actual &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = minExclusive.hashCode()
        result = 31 * result + actual.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String =
        "GreaterThanViolation(minExclusive=$minExclusive, actual=$actual, validationPath=$validationPath, reason=$reason)"
}

public class LessThanViolation<T : Comparable<T>>(
    public val maxExclusive: T,
    public val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LessThanViolation<*>) return false

        return maxExclusive == other.maxExclusive &&
            actual == other.actual &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = maxExclusive.hashCode()
        result = 31 * result + actual.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String =
        "LessThanViolation(maxExclusive=$maxExclusive, actual=$actual, validationPath=$validationPath, reason=$reason)"
}
