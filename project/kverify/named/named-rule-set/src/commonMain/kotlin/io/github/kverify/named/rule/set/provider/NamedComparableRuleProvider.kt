package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedRule

public interface NamedComparableRuleProvider {
    public fun <T : Comparable<T>> namedBetween(range: ClosedRange<T>): NamedRule<T>

    public fun <T : Comparable<T>> namedEqualTo(other: T): NamedRule<T>

    public fun <T : Comparable<T>> namedGreaterThanOrEqualTo(other: T): NamedRule<T>

    public fun <T : Comparable<T>> namedGreaterThan(other: T): NamedRule<T>

    public fun <T : Comparable<T>> namedLessThanOrEqualTo(other: T): NamedRule<T>

    public fun <T : Comparable<T>> namedLessThan(other: T): NamedRule<T>

    public fun <T : Comparable<T>> namedNotBetween(range: ClosedRange<T>): NamedRule<T>

    public fun <T : Comparable<T>> namedNotEqualTo(other: T): NamedRule<T>
}

public interface NamedComparableRuleWithFactoryProvider {
    public fun <T : Comparable<T>> namedBetween(
        range: ClosedRange<T>,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedGreaterThanOrEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedGreaterThan(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedLessThanOrEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedLessThan(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedNotBetween(
        range: ClosedRange<T>,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedNotEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T>
}
