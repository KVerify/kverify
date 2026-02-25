package io.github.kverify.core.rule

import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.scope.failIf

public open class PredicateRule<in T>(
    public val validationCheck: ValidationCheck<T>,
    public val violationFactory: ViolationFactory<T>,
) : Rule<T> {
    final override fun execute(
        scope: ValidationScope,
        value: T,
    ) {
        val isValid = validationCheck.isValid(scope, value)

        scope.failIf(!isValid) {
            violationFactory.createViolation(scope, value)
        }
    }
}
