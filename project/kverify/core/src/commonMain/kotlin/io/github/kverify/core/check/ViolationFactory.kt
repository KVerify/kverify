package io.github.kverify.core.check

import io.github.kverify.core.violation.Violation

public fun interface ViolationFactory<in T> {
    public fun createViolation(value: T): Violation
}
