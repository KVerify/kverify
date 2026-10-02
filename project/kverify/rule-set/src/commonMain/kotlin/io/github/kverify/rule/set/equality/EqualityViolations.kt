package io.github.kverify.rule.set.equality

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.violation.PathAwareViolation

public data class NotNullViolation(
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class EqualToViolation<T>(
    public val expected: T,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class NotEqualToViolation<T>(
    public val forbidden: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class OneOfViolation<T>(
    public val allowed: Set<T>,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public data class NoneOfViolation<T>(
    public val forbidden: Set<T>,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
