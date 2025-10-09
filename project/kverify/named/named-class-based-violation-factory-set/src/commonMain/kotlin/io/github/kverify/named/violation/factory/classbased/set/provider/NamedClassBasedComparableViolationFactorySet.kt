package io.github.kverify.named.violation.factory.classbased.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.comparable.NamedComparableBetweenViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.comparable.NamedComparableEqualToViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.comparable.NamedComparableGreaterThanOrEqualToViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.comparable.NamedComparableGreaterThanViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.comparable.NamedComparableLessThanOrEqualToViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.comparable.NamedComparableLessThanViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.comparable.NamedComparableNotBetweenViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.comparable.NamedComparableNotEqualToViolationFactory
import io.github.kverify.named.violation.factory.provider.NamedComparableViolationFactoryProvider
import io.github.kverify.violation.set.provider.ComparableViolationProvider

public class NamedClassBasedComparableViolationFactorySet(
    public val comparableViolationProvider: ComparableViolationProvider = ComparableViolationProvider.Default,
) : NamedComparableViolationFactoryProvider {
    override fun <T : Comparable<T>> namedBetween(range: ClosedRange<T>): NamedViolationFactory<T> =
        NamedComparableBetweenViolationFactory(
            range = range,
            comparableViolationProvider = comparableViolationProvider,
        )

    override fun <T : Comparable<T>> namedEqualTo(other: T): NamedViolationFactory<T> =
        NamedComparableEqualToViolationFactory(
            other = other,
            comparableViolationProvider = comparableViolationProvider,
        )

    override fun <T : Comparable<T>> namedGreaterThanOrEqualTo(other: T): NamedViolationFactory<T> =
        NamedComparableGreaterThanOrEqualToViolationFactory(
            other = other,
            comparableViolationProvider = comparableViolationProvider,
        )

    override fun <T : Comparable<T>> namedGreaterThan(other: T): NamedViolationFactory<T> =
        NamedComparableGreaterThanViolationFactory(
            other = other,
            comparableViolationProvider = comparableViolationProvider,
        )

    override fun <T : Comparable<T>> namedLessThanOrEqualTo(other: T): NamedViolationFactory<T> =
        NamedComparableLessThanOrEqualToViolationFactory(
            other = other,
            comparableViolationProvider = comparableViolationProvider,
        )

    override fun <T : Comparable<T>> namedLessThan(other: T): NamedViolationFactory<T> =
        NamedComparableLessThanViolationFactory(
            other = other,
            comparableViolationProvider = comparableViolationProvider,
        )

    override fun <T : Comparable<T>> namedNotBetween(range: ClosedRange<T>): NamedViolationFactory<T> =
        NamedComparableNotBetweenViolationFactory(
            range = range,
            comparableViolationProvider = comparableViolationProvider,
        )

    override fun <T : Comparable<T>> namedNotEqualTo(other: T): NamedViolationFactory<T> =
        NamedComparableNotEqualToViolationFactory(
            other = other,
            comparableViolationProvider = comparableViolationProvider,
        )
}
