package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public data class MaxLengthViolation(
    val maxLengthAllowed: Int,
    val actualLength: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation
