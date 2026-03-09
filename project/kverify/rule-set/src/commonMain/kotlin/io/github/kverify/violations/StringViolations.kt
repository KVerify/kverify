package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public class NotBlankViolation(
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NotBlankViolation) return false

        return validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String = "NotBlankViolation(validationPath=$validationPath, reason=$reason)"
}

public class MinLengthViolation(
    public val minLengthAllowed: Int,
    public val actualLength: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MinLengthViolation) return false

        return minLengthAllowed == other.minLengthAllowed &&
            actualLength == other.actualLength &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = minLengthAllowed.hashCode()
        result = 31 * result + actualLength.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String =
        "MinLengthViolation(minLengthAllowed=$minLengthAllowed, actualLength=$actualLength, validationPath=$validationPath, reason=$reason)"
}

public class MaxLengthViolation(
    public val maxLengthAllowed: Int,
    public val actualLength: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MaxLengthViolation) return false

        return maxLengthAllowed == other.maxLengthAllowed &&
            actualLength == other.actualLength &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = maxLengthAllowed.hashCode()
        result = 31 * result + actualLength.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String =
        "MaxLengthViolation(maxLengthAllowed=$maxLengthAllowed, actualLength=$actualLength, validationPath=$validationPath, reason=$reason)"
}

public class ExactLengthViolation(
    public val expectedLength: Int,
    public val actualLength: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ExactLengthViolation) return false

        return expectedLength == other.expectedLength &&
            actualLength == other.actualLength &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = expectedLength.hashCode()
        result = 31 * result + actualLength.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String =
        "ExactLengthViolation(expectedLength=$expectedLength, actualLength=$actualLength, validationPath=$validationPath, reason=$reason)"
}

public class LengthRangeViolation(
    public val minLengthAllowed: Int,
    public val maxLengthAllowed: Int,
    public val actualLength: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LengthRangeViolation) return false

        return minLengthAllowed == other.minLengthAllowed &&
            maxLengthAllowed == other.maxLengthAllowed &&
            actualLength == other.actualLength &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = minLengthAllowed.hashCode()
        result = 31 * result + maxLengthAllowed.hashCode()
        result = 31 * result + actualLength.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String =
        "LengthRangeViolation(minLengthAllowed=$minLengthAllowed, maxLengthAllowed=$maxLengthAllowed, actualLength=$actualLength, validationPath=$validationPath, reason=$reason)"
}
