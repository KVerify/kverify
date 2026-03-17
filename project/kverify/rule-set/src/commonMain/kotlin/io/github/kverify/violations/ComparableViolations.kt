package io.github.kverify.violations

import io.github.kverify.core.model.ValidationPath

public data class AtLeastViolation<T : Comparable<T>>(
    public val minAllowed: T,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class AtMostViolation<T : Comparable<T>>(
    public val maxAllowed: T,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class BetweenViolation<T : Comparable<T>>(
    public val min: T,
    public val max: T,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class GreaterThanViolation<T : Comparable<T>>(
    public val minExclusive: T,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class LessThanViolation<T : Comparable<T>>(
    public val maxExclusive: T,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
