package io.github.kverify.violation.factory.set.string

import io.github.kverify.core.model.pathElements
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.string.ExactLengthViolation

public class ExactLengthViolationFactory(
    public val length: Int,
    public val reason: String? = null,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): ExactLengthViolation {
        val actualLength = value.length
        return ExactLengthViolation(
            expectedLength = length,
            actualLength = actualLength,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be exactly $length characters long. Actual length: $actualLength",
        )
    }
}
