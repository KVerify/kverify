package io.github.kverify.core.rule.predicate

import io.github.kverify.core.violation.Violation

/**
 * A factory that creates [Violation] instances for given values.
 *
 * Implementations define how to construct a violation based on the provided value.
 *
 * @see PredicateRule
 */
public fun interface ViolationFactory<in T> {
    /**
     * Creates a [Violation] for the given [value].
     */
    public fun createViolation(value: T): Violation
}
