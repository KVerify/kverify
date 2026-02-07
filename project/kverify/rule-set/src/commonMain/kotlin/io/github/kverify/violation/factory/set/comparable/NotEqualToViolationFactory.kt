package io.github.kverify.violation.factory.set.comparable

import io.github.kverify.core.context.element.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.comparable.NotEqualToViolation

public class NotEqualToViolationFactory<T : Comparable<T>>(
    public val forbidden: T,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): NotEqualToViolation<T> =
        NotEqualToViolation(
            forbidden = forbidden,
            validationPath = scope.validationContext.filterPathElements(),
        )
}
