package io.github.kverify.violation.factory.set.string

import io.github.kverify.core.context.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
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
            validationPath = scope.validationContext.filterPathElements(),
            reason = reason ?: "Value must be between $min and $max characters long. Actual length: $actualLength",
        )
    }
}
