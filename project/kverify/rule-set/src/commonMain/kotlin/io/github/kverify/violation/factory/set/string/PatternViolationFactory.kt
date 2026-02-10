package io.github.kverify.violation.factory.set.string

import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.violation.set.string.PatternViolation

public class PatternViolationFactory(
    public val regex: Regex,
    public val reason: String? = null,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): PatternViolation =
        PatternViolation(
            regex = regex,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must match the pattern: $regex",
        )
}
