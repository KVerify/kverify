package io.github.kverify.violation.set.collection

import io.github.kverify.core.ValidationPath
import io.github.kverify.violation.set.PathAwareViolation

public data class SizeRangeViolation(
    val minSizeAllowed: Int,
    val maxSizeAllowed: Int,
    val actualSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
