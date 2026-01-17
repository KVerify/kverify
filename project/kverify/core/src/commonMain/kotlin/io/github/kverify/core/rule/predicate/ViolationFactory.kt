package io.github.kverify.core.rule.predicate

import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.violation.Violation

public fun interface ViolationFactory<in T> {
    public fun createViolation(
        scope: ValidationScope,
        value: T,
    ): Violation
}
