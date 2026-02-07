package io.github.kverify.violation.factory.set.comparable

import io.github.kverify.core.context.element.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.comparable.LessThanViolation

public class LessThanViolationFactory<T : Comparable<T>>(
    public val max: T,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): LessThanViolation<T> =
        LessThanViolation(
            maxExclusive = max,
            actual = value,
            validationPath = scope.validationContext.filterPathElements(),
        )
}
