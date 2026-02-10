package io.github.kverify.violation.factory.set.comparable

import io.github.kverify.core.model.pathElements
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
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be greater than $min. Actual: $value",
        )
}
