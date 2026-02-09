package io.github.kverify.violation.factory.set.comparable

import io.github.kverify.core.context.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.comparable.BetweenViolation

public class BetweenViolationFactory<T : Comparable<T>>(
    public val min: T,
    public val max: T,
    public val reason: String? = null,
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
            reason = reason ?: "Value must be between $min and $max. Actual: $value",
        )
}
