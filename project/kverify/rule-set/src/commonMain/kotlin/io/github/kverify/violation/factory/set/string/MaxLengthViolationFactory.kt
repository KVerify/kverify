package io.github.kverify.violation.factory.set.string

import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.violation.set.string.MaxLengthViolation

public class MaxLengthViolationFactory(
    public val max: Int,
    public val reason: String? = null,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): MaxLengthViolation {
        val actualLength = value.length
        return MaxLengthViolation(
            maxLengthAllowed = max,
            actualLength = actualLength,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be at most $max characters long. Actual length: $actualLength",
        )
    }
}
