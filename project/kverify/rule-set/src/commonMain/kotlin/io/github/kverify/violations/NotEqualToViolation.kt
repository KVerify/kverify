package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public data class NotEqualToViolation<T>(
    val forbidden: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation
