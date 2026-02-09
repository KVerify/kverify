package io.github.kverify.violation.set.string

import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.violation.set.PathAwareViolation

public data class LengthRangeViolation(
    val minLengthAllowed: Int,
    val maxLengthAllowed: Int,
    val actualLength: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation
