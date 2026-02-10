package io.github.kverify.violation.set.comparable

import io.github.kverify.core.ValidationPath
import io.github.kverify.violation.set.PathAwareViolation

public data class AtMostViolation<T : Comparable<T>>(
    val maxAllowed: T,
    val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
