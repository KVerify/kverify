package io.github.kverify.core.rule

import io.github.kverify.core.check.ValidationCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.failIfNot

/**
 * Rule that checks a value using a [validationCheck]
 * and produces a [io.github.kverify.core.violation.Violation] via [violationFactory] when the check fails.
 */
public class PredicateRule<in T>(
    public val validationCheck: ValidationCheck<T>,
    public val violationFactory: ViolationFactory<T>,
) : Rule<T> {
    override fun run(
        context: ValidationContext,
        value: T,
    ) {
        val isValid = validationCheck.isValid(value)

        context.failIfNot(isValid) {
            violationFactory.createViolation(value)
        }
    }
}
