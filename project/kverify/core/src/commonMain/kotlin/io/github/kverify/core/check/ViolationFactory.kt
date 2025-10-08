package io.github.kverify.core.check

import io.github.kverify.core.violation.Violation

/**
 * Factory that creates a [Violation] for a given value.
 */
public fun interface ViolationFactory<in T> {
    /**
     * Produces a [Violation] that describes why [value] is invalid.
     */
    public fun createViolation(value: T): Violation
}
