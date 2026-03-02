package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public data class AtLeastViolation<T : Comparable<T>>(
    val minAllowed: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation

public data class AtMostViolation<T : Comparable<T>>(
    val maxAllowed: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation

public data class BetweenViolation<T : Comparable<T>>(
    val min: T,
    val max: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation

public data class GreaterThanViolation<T : Comparable<T>>(
    val minExclusive: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation

public data class LessThanViolation<T : Comparable<T>>(
    val maxExclusive: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation
