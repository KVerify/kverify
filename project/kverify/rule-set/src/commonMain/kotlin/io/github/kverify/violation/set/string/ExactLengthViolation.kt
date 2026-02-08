package io.github.kverify.violation.set.string

import io.github.kverify.core.context.element.ValidationPathElement
import io.github.kverify.violation.set.PathAwareViolation

public data class ExactLengthViolation(
    val expectedLength: Int,
    val actualLength: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation
