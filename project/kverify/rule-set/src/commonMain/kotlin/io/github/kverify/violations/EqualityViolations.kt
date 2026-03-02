package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public data class EqualToViolation<T>(
    val expected: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation

public data class NotEqualToViolation<T>(
    val forbidden: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation

public data class OneOfViolation<T>(
    val allowed: Set<T>,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation

public data class NoneOfViolation<T>(
    val forbidden: Set<T>,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation
