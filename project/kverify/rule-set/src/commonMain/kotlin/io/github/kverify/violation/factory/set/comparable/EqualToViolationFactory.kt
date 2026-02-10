package io.github.kverify.violation.factory.set.comparable

import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.violation.set.comparable.EqualToViolation

public class EqualToViolationFactory<T : Comparable<T>>(
    public val expected: T,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): EqualToViolation<T> =
        EqualToViolation(
            expected = expected,
            actual = value,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be equal to $expected. Actual: $value",
        )
}
