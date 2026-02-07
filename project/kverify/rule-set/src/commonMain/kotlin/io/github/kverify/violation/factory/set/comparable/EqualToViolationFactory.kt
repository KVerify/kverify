package io.github.kverify.violation.factory.set.comparable

import io.github.kverify.core.context.element.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.comparable.EqualToViolation

public class EqualToViolationFactory<T : Comparable<T>>(
    public val expected: T,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): EqualToViolation<T> =
        EqualToViolation(
            expected = expected,
            actual = value,
            validationPath = scope.validationContext.filterPathElements(),
        )
}
