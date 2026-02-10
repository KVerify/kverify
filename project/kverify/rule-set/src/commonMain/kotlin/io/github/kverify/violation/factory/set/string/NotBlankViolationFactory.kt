package io.github.kverify.violation.factory.set.string

import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.violation.set.string.NotBlankViolation

public class NotBlankViolationFactory(
    public val reason: String? = null,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): NotBlankViolation =
        NotBlankViolation(
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must not be blank",
        )
}
