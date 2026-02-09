package io.github.kverify.violation.factory.set.comparable

import io.github.kverify.core.context.filterPathElements
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.comparable.GreaterThanViolation

public class GreaterThanViolationFactory<T : Comparable<T>>(
    public val min: T,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): GreaterThanViolation<T> =
        GreaterThanViolation(
            minExclusive = min,
            actual = value,
            validationPath = scope.validationContext.filterPathElements(),
            reason = reason ?: "Value must be greater than $min. Actual: $value",
        )
}
