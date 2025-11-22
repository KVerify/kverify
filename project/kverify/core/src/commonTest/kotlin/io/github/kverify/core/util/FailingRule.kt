package io.github.kverify.core.util

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolationReason

class FailingRule<in T>(
    val violationFactory: ViolationFactory<T>,
) : Rule<T> by StubRule(
        fails = true,
        violationFactory = violationFactory,
    ) {
    constructor(
        violation: Violation,
    ) : this({ violation })

    constructor(
        violationReason: String,
    ) : this(
        violationReason.asViolationReason(),
    )
}
