package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public data class AtMostViolation<T : Comparable<T>>(
    val maxAllowed: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation
