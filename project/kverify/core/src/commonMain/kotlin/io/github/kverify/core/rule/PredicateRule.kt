package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.violation.Violation

public fun interface ValidationCheck<T> {
    public fun isValid(value: T): Boolean
}

public fun interface ViolationFactory<T> {
    public fun createViolation(value: T): Violation
}

public class PredicateRule<T>(
    public val validationCheck: ValidationCheck<T>,
    public val violationFactory: ViolationFactory<T>,
) : Rule<T> {
    override fun ValidationContext.runValidation(value: T) {
        val isValid = validationCheck.isValid(value)

        validate(isValid) {
            violationFactory.createViolation(value)
        }
    }
}
