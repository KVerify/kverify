package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public class EqualToViolation<T>(
    public val expected: T,
    public val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EqualToViolation<*>) return false

        return expected == other.expected &&
            actual == other.actual &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = expected.hashCode()
        result = 31 * result + actual.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String = "EqualToViolation(expected=$expected, actual=$actual, validationPath=$validationPath, reason=$reason)"
}

public class NotEqualToViolation<T>(
    public val forbidden: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NotEqualToViolation<*>) return false

        return forbidden == other.forbidden &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = forbidden.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String = "NotEqualToViolation(forbidden=$forbidden, validationPath=$validationPath, reason=$reason)"
}

public class OneOfViolation<T>(
    public val allowed: Set<T>,
    public val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OneOfViolation<*>) return false

        return allowed == other.allowed &&
            actual == other.actual &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = allowed.hashCode()
        result = 31 * result + actual.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String = "OneOfViolation(allowed=$allowed, actual=$actual, validationPath=$validationPath, reason=$reason)"
}

public class NoneOfViolation<T>(
    public val forbidden: Set<T>,
    public val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NoneOfViolation<*>) return false

        return forbidden == other.forbidden &&
            actual == other.actual &&
            validationPath == other.validationPath &&
            reason == other.reason
    }

    override fun hashCode(): Int {
        var result = forbidden.hashCode()
        result = 31 * result + actual.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()

        return result
    }

    override fun toString(): String =
        "NoneOfViolation(forbidden=$forbidden, actual=$actual, validationPath=$validationPath, reason=$reason)"
}
