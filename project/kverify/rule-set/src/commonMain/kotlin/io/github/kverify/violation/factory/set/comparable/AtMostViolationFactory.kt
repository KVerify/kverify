package io.github.kverify.violation.factory.set.comparable

import io.github.kverify.core.context.element.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.comparable.AtMostViolation

public class AtMostViolationFactory<T : Comparable<T>>(
    public val max: T,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): AtMostViolation<T> =
        AtMostViolation(
            maxAllowed = max,
            actual = value,
            validationPath = scope.validationContext.filterPathElements(),
        )
}
