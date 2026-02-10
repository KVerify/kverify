package io.github.kverify.violation.set.string

import io.github.kverify.core.ValidationPath
import io.github.kverify.violation.set.PathAwareViolation

public data class NotBlankViolation(
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
