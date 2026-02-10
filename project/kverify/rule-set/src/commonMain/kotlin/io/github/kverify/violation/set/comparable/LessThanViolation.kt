package io.github.kverify.violation.set.comparable

import io.github.kverify.core.ValidationPath
import io.github.kverify.violation.set.PathAwareViolation

public data class LessThanViolation<T : Comparable<T>>(
    val maxExclusive: T,
    val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
