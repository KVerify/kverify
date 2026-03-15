package io.github.kverify.violations

import io.github.kverify.core.model.ValidationPath

public class NotBlankViolation(
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String = "NotBlankViolation(validationPath=$validationPath, reason=$reason)"
}

public class MinLengthViolation(
    public val minLengthAllowed: Int,
    public val actualLength: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "MinLengthViolation(minLengthAllowed=$minLengthAllowed, actualLength=$actualLength, validationPath=$validationPath, reason=$reason)"
}

public class MaxLengthViolation(
    public val maxLengthAllowed: Int,
    public val actualLength: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "MaxLengthViolation(maxLengthAllowed=$maxLengthAllowed, actualLength=$actualLength, validationPath=$validationPath, reason=$reason)"
}

public class ExactLengthViolation(
    public val expectedLength: Int,
    public val actualLength: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "ExactLengthViolation(expectedLength=$expectedLength, actualLength=$actualLength, validationPath=$validationPath, reason=$reason)"
}

public class LengthRangeViolation(
    public val minLengthAllowed: Int,
    public val maxLengthAllowed: Int,
    public val actualLength: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "LengthRangeViolation(minLengthAllowed=$minLengthAllowed, maxLengthAllowed=$maxLengthAllowed, actualLength=$actualLength, validationPath=$validationPath, reason=$reason)"
}
