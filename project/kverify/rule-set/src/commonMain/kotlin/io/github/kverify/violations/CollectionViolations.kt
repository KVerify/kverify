package io.github.kverify.violations

import io.github.kverify.core.model.ValidationPath

public data class MinSizeViolation(
    public val minSizeAllowed: Int,
    public val actualSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class MaxSizeViolation(
    public val maxSizeAllowed: Int,
    public val actualSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class ExactSizeViolation(
    public val expectedSize: Int,
    public val actualSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class SizeRangeViolation(
    public val minSizeAllowed: Int,
    public val maxSizeAllowed: Int,
    public val actualSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class DistinctViolation(
    public val actualSize: Int,
    public val distinctSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
