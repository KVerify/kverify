package io.github.kverify.core.rule.predicate

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.failIfNot
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.check.ValidationCheck

public class PredicateRule<in T>(
    public val validationCheck: ValidationCheck<T>,
    public val violationFactory: ViolationFactory<T>,
) : Rule<T> {
    override fun execute(
        context: ValidationContext,
        value: T,
    ) {
        val isValid = validationCheck.isValid(value)

        context.failIfNot(isValid) {
            violationFactory.createViolation(value)
        }
    }
}
