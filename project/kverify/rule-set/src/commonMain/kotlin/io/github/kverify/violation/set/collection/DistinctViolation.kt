package io.github.kverify.violation.set.collection

import io.github.kverify.core.context.element.ValidationPathElement
import io.github.kverify.violation.set.PathAwareViolation

public data class DistinctViolation(
    val actualSize: Int,
    val distinctSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation
