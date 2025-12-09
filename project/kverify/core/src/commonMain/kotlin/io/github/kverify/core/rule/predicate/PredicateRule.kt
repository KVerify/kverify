package io.github.kverify.core.rule.predicate

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.failIfNot
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.check.ValidationCheck

/**
 * A [Rule] that validates values using [validationCheck] and reports violations created via [violationFactory].
 *
 * @see ValidationCheck
 * @see ViolationFactory
 */
public class PredicateRule<in T>(
    public val validationCheck: ValidationCheck<T>,
    public val violationFactory: ViolationFactory<T>,
) : Rule<T> {
    /**
     * If [validationCheck] returns `false`, a [io.github.kverify.core.violation.Violation]
     * is created using [violationFactory] and reported to the [context].
     */
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
