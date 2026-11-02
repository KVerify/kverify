package io.github.kverify.rule.set.violation

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.violation.PathAwareViolation

public class AtLeastViolation<T : Comparable<T>>(
    public val min: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
