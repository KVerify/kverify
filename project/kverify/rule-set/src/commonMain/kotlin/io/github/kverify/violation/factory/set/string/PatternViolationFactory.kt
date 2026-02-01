package io.github.kverify.violation.factory.set.string

import io.github.kverify.core.context.element.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.string.PatternViolation

public class PatternViolationFactory(
    public val regex: Regex,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): PatternViolation =
        PatternViolation(
            regex = regex,
            validationPath = scope.validationContext.filterPathElements(),
        )
}
