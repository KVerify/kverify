package io.github.kverify.violation.factory.set.string

import io.github.kverify.core.context.element.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.string.MinLengthViolation

public class MinLengthViolationFactory(
    public val min: Int,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): MinLengthViolation =
        MinLengthViolation(
            minLengthAllowed = min,
            actualLength = value.length,
            validationPath = scope.validationContext.filterPathElements(),
        )
}
