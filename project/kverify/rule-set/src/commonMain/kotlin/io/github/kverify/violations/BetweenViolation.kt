package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public data class BetweenViolation<T : Comparable<T>>(
    val min: T,
    val max: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation
