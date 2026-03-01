package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public data class DistinctViolation(
    val actualSize: Int,
    val distinctSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation
