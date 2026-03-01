package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public data class ExactSizeViolation(
    val expectedSize: Int,
    val actualSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation
