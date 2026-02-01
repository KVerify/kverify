package io.github.kverify.violation.factory.set.string

import io.github.kverify.core.context.element.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.string.LengthRangeViolation

public class LengthRangeViolationFactory(
    public val min: Int,
    public val max: Int,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): LengthRangeViolation =
        LengthRangeViolation(
            minLengthAllowed = min,
            maxLengthAllowed = max,
            actualLength = value.length,
            validationPath = scope.validationContext.filterPathElements(),
        )
}
