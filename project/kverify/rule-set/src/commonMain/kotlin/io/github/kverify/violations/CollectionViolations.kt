package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public class MinSizeViolation(
    public val minSizeAllowed: Int,
    public val actualSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MinSizeViolation) return false

        return minSizeAllowed == other.minSizeAllowed &&
            actualSize == other.actualSize &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = minSizeAllowed.hashCode()
        result = 31 * result + actualSize.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String =
        "MinSizeViolation(minSizeAllowed=$minSizeAllowed, actualSize=$actualSize, validationPath=$validationPath, reason=$reason)"
}

public class MaxSizeViolation(
    public val maxSizeAllowed: Int,
    public val actualSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MaxSizeViolation) return false

        return maxSizeAllowed == other.maxSizeAllowed &&
            actualSize == other.actualSize &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = maxSizeAllowed.hashCode()
        result = 31 * result + actualSize.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String =
        "MaxSizeViolation(maxSizeAllowed=$maxSizeAllowed, actualSize=$actualSize, validationPath=$validationPath, reason=$reason)"
}

public class ExactSizeViolation(
    public val expectedSize: Int,
    public val actualSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ExactSizeViolation) return false

        return expectedSize == other.expectedSize &&
            actualSize == other.actualSize &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = expectedSize.hashCode()
        result = 31 * result + actualSize.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String =
        "ExactSizeViolation(expectedSize=$expectedSize, actualSize=$actualSize, validationPath=$validationPath, reason=$reason)"
}

public class SizeRangeViolation(
    public val minSizeAllowed: Int,
    public val maxSizeAllowed: Int,
    public val actualSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SizeRangeViolation) return false

        return minSizeAllowed == other.minSizeAllowed &&
            maxSizeAllowed == other.maxSizeAllowed &&
            actualSize == other.actualSize &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = minSizeAllowed.hashCode()
        result = 31 * result + maxSizeAllowed.hashCode()
        result = 31 * result + actualSize.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String =
        "SizeRangeViolation(minSizeAllowed=$minSizeAllowed, maxSizeAllowed=$maxSizeAllowed, actualSize=$actualSize, validationPath=$validationPath, reason=$reason)"
}

public class DistinctViolation(
    public val actualSize: Int,
    public val distinctSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DistinctViolation) return false

        return actualSize == other.actualSize &&
            distinctSize == other.distinctSize &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = actualSize.hashCode()
        result = 31 * result + distinctSize.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String =
        "DistinctViolation(actualSize=$actualSize, distinctSize=$distinctSize, validationPath=$validationPath, reason=$reason)"
}
