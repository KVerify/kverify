package io.github.kverify.violation.factory.set.enumeration

import io.github.kverify.core.model.pathElements
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.enumeration.NoneOfViolation

public class NoneOfViolationFactory<T>(
    public val forbidden: Set<T>,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): NoneOfViolation<T> =
        NoneOfViolation(
            forbidden = forbidden,
            actual = value,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must not be one of $forbidden. Actual: $value",
        )
}
