package io.github.kverify.violation.factory.set.enumeration

import io.github.kverify.core.context.element.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.enumeration.NoneOfViolation

public class NoneOfViolationFactory<T>(
    public val forbidden: Set<T>,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): NoneOfViolation<T> =
        NoneOfViolation(
            forbidden = forbidden,
            actual = value,
            validationPath = scope.validationContext.filterPathElements(),
        )
}
