package io.github.kverify.violation.factory

import io.github.kverify.core.violation.Violation

public fun interface ViolationFactory<T> {
    public fun createViolation(value: T): Violation
}
