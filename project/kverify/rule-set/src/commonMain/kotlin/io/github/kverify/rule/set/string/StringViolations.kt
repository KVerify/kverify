package io.github.kverify.rule.set.string

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.violation.PathAwareViolation

public data class NotBlankViolation(
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class MinLengthViolation(
    public val minLengthAllowed: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class MaxLengthViolation(
    public val maxLengthAllowed: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class ExactLengthViolation(
    public val expectedLength: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class LengthRangeViolation(
    public val minLengthAllowed: Int,
    public val maxLengthAllowed: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class PatternViolation(
    public val pattern: String,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
