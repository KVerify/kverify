package io.github.kverify.violation.set.collection

import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.violation.set.PathAwareViolation

public data class MaxSizeViolation(
    val maxSizeAllowed: Int,
    val actualSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation
