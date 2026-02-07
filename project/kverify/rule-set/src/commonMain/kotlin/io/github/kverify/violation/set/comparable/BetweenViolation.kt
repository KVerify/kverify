package io.github.kverify.violation.set.comparable

import io.github.kverify.core.context.element.ValidationPathElement
import io.github.kverify.violation.set.PathAwareViolation

public data class BetweenViolation<T : Comparable<T>>(
    val min: T,
    val max: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String = "Value must be between $min and $max. Actual: $actual",
) : PathAwareViolation
