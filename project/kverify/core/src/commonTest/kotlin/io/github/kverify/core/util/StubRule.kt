package io.github.kverify.core.util

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolationReason

class StubRule<in T>(
    val fails: Boolean,
    val violationFactory: ViolationFactory<T>,
) : Rule<T> {
    constructor(
        fails: Boolean,
        violation: Violation,
    ) : this(
        fails,
        violationFactory = { violation },
    )

    constructor(
        fails: Boolean,
        violationReason: String,
    ) : this(
        fails,
        violation = violationReason.asViolationReason(),
    )

    override fun execute(
        context: ValidationContext,
        value: T,
    ) {
        if (fails) {
            context.onFailure(violationFactory.createViolation(value))
        }
    }
}
