package io.github.kverify.violation.set.enumeration

import io.github.kverify.core.ValidationPath
import io.github.kverify.violation.set.PathAwareViolation

public data class OneOfViolation<T>(
    val allowed: Set<T>,
    val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
