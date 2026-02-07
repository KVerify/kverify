package io.github.kverify.violation.set.comparable

import io.github.kverify.core.context.element.ValidationPathElement
import io.github.kverify.violation.set.PathAwareViolation

public data class NotEqualToViolation<T : Comparable<T>>(
    val forbidden: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation
