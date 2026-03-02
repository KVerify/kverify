package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public data class NotBlankViolation(
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation

public data class MinLengthViolation(
    val minLengthAllowed: Int,
    val actualLength: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation

public data class MaxLengthViolation(
    val maxLengthAllowed: Int,
    val actualLength: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation

public data class ExactLengthViolation(
    val expectedLength: Int,
    val actualLength: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation

public data class LengthRangeViolation(
    val minLengthAllowed: Int,
    val maxLengthAllowed: Int,
    val actualLength: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation
