package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedComparableViolationFactoryProvider

public interface NamedComparableRuleProvider {
    public val namedComparableViolationFactoryProvider: NamedComparableViolationFactoryProvider
        get() = NamedComparableViolationFactoryProvider.Default

    public fun <T : Comparable<T>> namedBetween(
        range: ClosedRange<T>,
        violationFactory: NamedViolationFactory<T> = namedComparableViolationFactoryProvider.namedBetween(range),
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T> = namedComparableViolationFactoryProvider.namedEqualTo(other),
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedGreaterThanOrEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T> =
            namedComparableViolationFactoryProvider.namedGreaterThanOrEqualTo(
                other,
            ),
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedGreaterThan(
        other: T,
        violationFactory: NamedViolationFactory<T> = namedComparableViolationFactoryProvider.namedGreaterThan(other),
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedLessThanOrEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T> =
            namedComparableViolationFactoryProvider.namedLessThanOrEqualTo(
                other,
            ),
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedLessThan(
        other: T,
        violationFactory: NamedViolationFactory<T> = namedComparableViolationFactoryProvider.namedLessThan(other),
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedNotBetween(
        range: ClosedRange<T>,
        violationFactory: NamedViolationFactory<T> = namedComparableViolationFactoryProvider.namedNotBetween(range),
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedNotEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T> = namedComparableViolationFactoryProvider.namedNotEqualTo(other),
    ): NamedRule<T>
}
