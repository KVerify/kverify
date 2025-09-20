package io.github.kverify.core.rule

import io.github.kverify.core.check.ValidationCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.failIfNot

public class PredicateRule<in T>(
    public val validationCheck: ValidationCheck<T>,
    public val violationFactory: ViolationFactory<T>,
) : Rule<T> {
    override fun ValidationContext.runValidation(value: T) {
        val isValid = validationCheck.isValid(value)

        this@runValidation.failIfNot(isValid) {
            violationFactory.createViolation(value)
        }
    }
}
