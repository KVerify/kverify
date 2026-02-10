package io.github.kverify.violation.factory.set.string

import io.github.kverify.core.model.pathElements
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.string.MinLengthViolation

public class MinLengthViolationFactory(
    public val min: Int,
    public val reason: String? = null,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): MinLengthViolation {
        val actualLength = value.length
        return MinLengthViolation(
            minLengthAllowed = min,
            actualLength = actualLength,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be at least $min characters long. Actual length: $actualLength",
        )
    }
}
