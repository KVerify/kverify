package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public data class MinSizeViolation(
    val minSizeAllowed: Int,
    val actualSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation
