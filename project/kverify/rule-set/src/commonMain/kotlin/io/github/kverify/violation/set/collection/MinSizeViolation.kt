package io.github.kverify.violation.set.collection

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.violation.set.PathAwareViolation

public data class MinSizeViolation(
    val minSizeAllowed: Int,
    val actualSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
