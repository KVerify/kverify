package io.github.kverify.violation.factory.set.enumeration

import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.violation.set.enumeration.OneOfViolation

public class OneOfViolationFactory<T>(
    public val allowed: Set<T>,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): OneOfViolation<T> =
        OneOfViolation(
            allowed = allowed,
            actual = value,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be one of $allowed. Actual: $value",
        )
}
