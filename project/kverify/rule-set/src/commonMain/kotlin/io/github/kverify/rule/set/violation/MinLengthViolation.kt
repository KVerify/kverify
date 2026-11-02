package io.github.kverify.rule.set.violation

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.violation.PathAwareViolation

public class MinLengthViolation(
    public val minLength: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
