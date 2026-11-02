package io.github.kverify.rule.set.collection

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.violation.PathAwareViolation

public data class MinSizeViolation(
    public val minSizeAllowed: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class MaxSizeViolation(
    public val maxSizeAllowed: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class ExactSizeViolation(
    public val expectedSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class SizeRangeViolation(
    public val minSizeAllowed: Int,
    public val maxSizeAllowed: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class DistinctViolation(
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
