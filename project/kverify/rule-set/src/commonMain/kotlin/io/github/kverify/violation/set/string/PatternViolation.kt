package io.github.kverify.violation.set.string

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.violation.set.PathAwareViolation

public data class PatternViolation(
    val regex: Regex,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
