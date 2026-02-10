package io.github.kverify.violation.set.collection

import io.github.kverify.core.ValidationPath
import io.github.kverify.violation.set.PathAwareViolation

public data class DistinctViolation(
    val actualSize: Int,
    val distinctSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
