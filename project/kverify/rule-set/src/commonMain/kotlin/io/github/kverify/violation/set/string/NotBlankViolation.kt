package io.github.kverify.violation.set.string

import io.github.kverify.core.context.element.ValidationPathElement
import io.github.kverify.violation.set.PathAwareViolation

public data class NotBlankViolation(
    override val validationPath: List<ValidationPathElement>,
    override val reason: String = "Value must not be blank.",
) : PathAwareViolation
