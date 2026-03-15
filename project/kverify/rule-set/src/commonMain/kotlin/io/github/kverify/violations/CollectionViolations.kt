package io.github.kverify.violations

import io.github.kverify.core.model.ValidationPath

public class MinSizeViolation(
    public val minSizeAllowed: Int,
    public val actualSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "MinSizeViolation(minSizeAllowed=$minSizeAllowed, actualSize=$actualSize, validationPath=$validationPath, reason=$reason)"
}

public class MaxSizeViolation(
    public val maxSizeAllowed: Int,
    public val actualSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "MaxSizeViolation(maxSizeAllowed=$maxSizeAllowed, actualSize=$actualSize, validationPath=$validationPath, reason=$reason)"
}

public class ExactSizeViolation(
    public val expectedSize: Int,
    public val actualSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "ExactSizeViolation(expectedSize=$expectedSize, actualSize=$actualSize, validationPath=$validationPath, reason=$reason)"
}

public class SizeRangeViolation(
    public val minSizeAllowed: Int,
    public val maxSizeAllowed: Int,
    public val actualSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "SizeRangeViolation(minSizeAllowed=$minSizeAllowed, maxSizeAllowed=$maxSizeAllowed, actualSize=$actualSize, validationPath=$validationPath, reason=$reason)"
}

public class DistinctViolation(
    public val actualSize: Int,
    public val distinctSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "DistinctViolation(actualSize=$actualSize, distinctSize=$distinctSize, validationPath=$validationPath, reason=$reason)"
}
