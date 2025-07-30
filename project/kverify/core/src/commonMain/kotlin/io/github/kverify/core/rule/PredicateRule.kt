package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.violation.Violation

public class PredicateRule<T>(
    public val predicate: (T) -> Boolean,
    public val violationGenerator: (T) -> Violation,
) : Rule<T> {
    override fun ValidationContext.runValidation(value: T) {
        val isValid = predicate(value)
        validate(isValid) { violationGenerator(value) }
    }
}
