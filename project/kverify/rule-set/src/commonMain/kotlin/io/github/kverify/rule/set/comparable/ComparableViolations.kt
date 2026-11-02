package io.github.kverify.rule.set.comparable

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.violation.PathAwareViolation

public data class AtLeastViolation<T : Comparable<T>>(
    public val minAllowed: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class AtMostViolation<T : Comparable<T>>(
    public val maxAllowed: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class BetweenViolation<T : Comparable<T>>(
    public val min: T,
    public val max: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class GreaterThanViolation<T : Comparable<T>>(
    public val minExclusive: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class LessThanViolation<T : Comparable<T>>(
    public val maxExclusive: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
