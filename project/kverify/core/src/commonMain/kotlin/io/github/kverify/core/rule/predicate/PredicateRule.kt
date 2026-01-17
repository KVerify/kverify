package io.github.kverify.core.rule.predicate

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.check.ValidationCheck
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.scope.failIfNot

public class PredicateRule<in T>(
    public val validationCheck: ValidationCheck<T>,
    public val violationFactory: ViolationFactory<T>,
) : Rule<T> {
    override fun execute(
        scope: ValidationScope,
        value: T,
    ) {
        val isValid = validationCheck.isValid(scope, value)

        scope.failIfNot(isValid) {
            violationFactory.createViolation(scope, value)
        }
    }
}
