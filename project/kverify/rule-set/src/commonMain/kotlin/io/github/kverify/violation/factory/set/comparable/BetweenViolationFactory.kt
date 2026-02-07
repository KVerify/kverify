package io.github.kverify.violation.factory.set.comparable

import io.github.kverify.core.context.element.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.comparable.BetweenViolation

public class BetweenViolationFactory<T : Comparable<T>>(
    public val min: T,
    public val max: T,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): BetweenViolation<T> =
        BetweenViolation(
            min = min,
            max = max,
            actual = value,
            validationPath = scope.validationContext.filterPathElements(),
        )
}
