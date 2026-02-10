package io.github.kverify.violation.factory.set.comparable

import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.violation.set.comparable.NotEqualToViolation

public class NotEqualToViolationFactory<T : Comparable<T>>(
    public val forbidden: T,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): NotEqualToViolation<T> =
        NotEqualToViolation(
            forbidden = forbidden,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must not be equal to $forbidden",
        )
}
