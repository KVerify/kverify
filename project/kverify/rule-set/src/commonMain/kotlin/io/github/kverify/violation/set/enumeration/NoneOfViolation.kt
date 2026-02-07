package io.github.kverify.violation.set.enumeration

import io.github.kverify.core.context.element.ValidationPathElement
import io.github.kverify.violation.set.PathAwareViolation

public data class NoneOfViolation<T>(
    val forbidden: Set<T>,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String = "Value must not be one of $forbidden. Actual: $actual",
) : PathAwareViolation
