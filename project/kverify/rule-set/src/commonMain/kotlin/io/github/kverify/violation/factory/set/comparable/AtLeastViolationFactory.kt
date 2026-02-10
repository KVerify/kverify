package io.github.kverify.violation.factory.set.comparable

import io.github.kverify.core.model.pathElements
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.comparable.AtLeastViolation

public class AtLeastViolationFactory<T : Comparable<T>>(
    public val min: T,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): AtLeastViolation<T> =
        AtLeastViolation(
            minAllowed = min,
            actual = value,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be at least $min. Actual: $value",
        )
}
