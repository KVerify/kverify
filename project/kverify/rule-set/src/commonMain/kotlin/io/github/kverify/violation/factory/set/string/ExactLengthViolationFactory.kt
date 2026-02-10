package io.github.kverify.violation.factory.set.string

import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
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
