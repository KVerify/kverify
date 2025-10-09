package io.github.kverify.named.violation.factory.provider

import io.github.kverify.named.check.NamedViolationFactory

public interface NamedComparableViolationFactoryProvider {
    public fun <T : Comparable<T>> namedBetween(range: ClosedRange<T>): NamedViolationFactory<T>

    public fun <T : Comparable<T>> namedEqualTo(other: T): NamedViolationFactory<T>

    public fun <T : Comparable<T>> namedGreaterThanOrEqualTo(other: T): NamedViolationFactory<T>

    public fun <T : Comparable<T>> namedGreaterThan(other: T): NamedViolationFactory<T>

    public fun <T : Comparable<T>> namedLessThanOrEqualTo(other: T): NamedViolationFactory<T>

    public fun <T : Comparable<T>> namedLessThan(other: T): NamedViolationFactory<T>

    public fun <T : Comparable<T>> namedNotBetween(range: ClosedRange<T>): NamedViolationFactory<T>

    public fun <T : Comparable<T>> namedNotEqualTo(other: T): NamedViolationFactory<T>

    public companion object {
        public val Default: NamedComparableViolationFactoryProvider = NamedComparableViolationFactorySet()
    }
}
