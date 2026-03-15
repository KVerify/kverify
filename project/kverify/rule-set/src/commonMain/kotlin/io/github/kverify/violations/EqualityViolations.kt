package io.github.kverify.violations

import io.github.kverify.core.model.ValidationPath

public class NotNullViolation(
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String = "NotNullViolation(validationPath=$validationPath, reason=$reason)"
}

public class EqualToViolation<T>(
    public val expected: T,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String = "EqualToViolation(expected=$expected, actual=$actual, validationPath=$validationPath, reason=$reason)"
}

public class NotEqualToViolation<T>(
    public val forbidden: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String = "NotEqualToViolation(forbidden=$forbidden, validationPath=$validationPath, reason=$reason)"
}

public class OneOfViolation<T>(
    public val allowed: Set<T>,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String = "OneOfViolation(allowed=$allowed, actual=$actual, validationPath=$validationPath, reason=$reason)"
}

public class NoneOfViolation<T>(
    public val forbidden: Set<T>,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "NoneOfViolation(forbidden=$forbidden, actual=$actual, validationPath=$validationPath, reason=$reason)"
}
