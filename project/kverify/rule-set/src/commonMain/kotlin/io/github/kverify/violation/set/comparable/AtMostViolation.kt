package io.github.kverify.violation.set.comparable

import io.github.kverify.core.context.element.ValidationPathElement
import io.github.kverify.violation.set.PathAwareViolation

public data class AtMostViolation<T : Comparable<T>>(
    val maxAllowed: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String = "Value must be at most $maxAllowed. Actual: $actual",
) : PathAwareViolation
