package io.github.kverify.violation.set.comparable

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.violation.set.PathAwareViolation

public data class NotEqualToViolation<T : Comparable<T>>(
    val forbidden: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
