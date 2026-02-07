package io.github.kverify.violation.set.string

import io.github.kverify.core.context.element.ValidationPathElement
import io.github.kverify.violation.set.PathAwareViolation

public data class PatternViolation(
    val regex: Regex,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation
