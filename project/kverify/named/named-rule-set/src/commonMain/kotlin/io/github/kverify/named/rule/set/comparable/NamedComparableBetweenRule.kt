package io.github.kverify.named.rule.set.comparable

import io.github.kverify.check.set.comparable.ComparableBetweenCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedComparableViolationFactoryProvider

public class NamedComparableBetweenRule<T : Comparable<T>>(
    public val range: ClosedRange<T>,
    public val violationFactory: NamedViolationFactory<T> =
        NamedComparableViolationFactoryProvider.Default.namedBetween(
            range = range,
        ),
) : NamedRule<T> by NamedPredicateRule(
        validationCheck = ComparableBetweenCheck(range),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> NamedComparableBetweenRule(
    min: T,
    max: T,
    violationFactory: NamedViolationFactory<T>,
): NamedComparableBetweenRule<T> =
    NamedComparableBetweenRule(
        range = min..max,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> NamedComparableBetweenRule(
    min: T,
    max: T,
): NamedComparableBetweenRule<T> =
    NamedComparableBetweenRule(
        range = min..max,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> NamedComparableBetweenRule(
    range: OpenEndRange<T>,
    violationFactory: NamedViolationFactory<T>,
): NamedComparableBetweenRule<T> =
    NamedComparableBetweenRule(
        range = range.start..range.endExclusive,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> NamedComparableBetweenRule(range: OpenEndRange<T>): NamedComparableBetweenRule<T> =
    NamedComparableBetweenRule(
        range = range.start..range.endExclusive,
    )
