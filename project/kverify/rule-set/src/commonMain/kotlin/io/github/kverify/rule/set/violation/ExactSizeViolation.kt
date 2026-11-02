package io.github.kverify.rule.set.violation

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.violation.PathAwareViolation

public class ExactSizeViolation(
    public val expectedSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
