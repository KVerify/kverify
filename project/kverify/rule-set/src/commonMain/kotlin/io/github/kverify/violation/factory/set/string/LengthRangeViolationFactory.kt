package io.github.kverify.violation.factory.set.string

import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.violation.set.string.LengthRangeViolation

public class LengthRangeViolationFactory(
    public val min: Int,
    public val max: Int,
    public val reason: String? = null,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): LengthRangeViolation {
        val actualLength = value.length
        return LengthRangeViolation(
            minLengthAllowed = min,
            maxLengthAllowed = max,
            actualLength = actualLength,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be between $min and $max characters long. Actual length: $actualLength",
        )
    }
}
