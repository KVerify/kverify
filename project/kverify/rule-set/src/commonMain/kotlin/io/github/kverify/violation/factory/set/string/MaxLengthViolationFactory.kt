package io.github.kverify.violation.factory.set.string

import io.github.kverify.core.context.element.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.string.MaxLengthViolation

public class MaxLengthViolationFactory(
    public val max: Int,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): MaxLengthViolation =
        MaxLengthViolation(
            maxLengthAllowed = max,
            actualLength = value.length,
            validationPath = scope.validationContext.filterPathElements(),
        )
}
