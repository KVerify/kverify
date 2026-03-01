package io.github.kverify.violations

import io.github.kverify.core.context.ValidationPathElement

public data class NotBlankViolation(
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : RuleSetViolation
