package io.github.kverify.violation.factory.set.comparable

import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.violation.set.comparable.AtMostViolation

public class AtMostViolationFactory<T : Comparable<T>>(
    public val max: T,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): AtMostViolation<T> =
        AtMostViolation(
            maxAllowed = max,
            actual = value,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be at most $max. Actual: $value",
        )
}
