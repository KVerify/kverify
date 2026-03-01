package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public data class LessThanViolation<T : Comparable<T>>(
    val maxExclusive: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation
