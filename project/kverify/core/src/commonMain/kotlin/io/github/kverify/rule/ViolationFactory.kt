package io.github.kverify.rule

import io.github.kverify.scope.ValidationScope

public fun interface ViolationFactory<in T> {
    public fun createViolation(
        scope: ValidationScope,
        value: T,
    ): Violation
}
