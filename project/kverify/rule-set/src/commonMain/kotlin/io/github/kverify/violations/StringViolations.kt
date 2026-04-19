package io.github.kverify.violations

import io.github.kverify.core.model.ValidationPath

public data class NotBlankViolation(
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class MinLengthViolation(
    public val minLengthAllowed: Int,
    public val actualLength: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class MaxLengthViolation(
    public val maxLengthAllowed: Int,
    public val actualLength: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class ExactLengthViolation(
    public val expectedLength: Int,
    public val actualLength: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class LengthRangeViolation(
    public val minLengthAllowed: Int,
    public val maxLengthAllowed: Int,
    public val actualLength: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class PatternViolation(
    public val pattern: String,
    public val actualValue: String,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
