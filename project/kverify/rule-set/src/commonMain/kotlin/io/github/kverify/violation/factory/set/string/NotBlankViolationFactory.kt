package io.github.kverify.violation.factory.set.string

import io.github.kverify.core.context.element.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.string.NotBlankViolation

public object NotBlankViolationFactory : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): Violation =
        NotBlankViolation(
            validationPath = scope.validationContext.filterPathElements(),
        )
}
