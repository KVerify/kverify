package io.github.kverify.core.rule

import io.github.kverify.core.scope.ValidationScope

public fun interface ViolationFactory<in T> {
    public fun createViolation(
        scope: ValidationScope,
        value: T,
    ): Violation
}
