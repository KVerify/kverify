package io.github.kverify.violation.factory.classbased.set.provider

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.violation.factory.classbased.set.comparable.ComparableBetweenViolationFactory
import io.github.kverify.violation.factory.classbased.set.comparable.ComparableEqualToViolationFactory
import io.github.kverify.violation.factory.classbased.set.comparable.ComparableGreaterThanOrEqualToViolationFactory
import io.github.kverify.violation.factory.classbased.set.comparable.ComparableGreaterThanViolationFactory
import io.github.kverify.violation.factory.classbased.set.comparable.ComparableLessThanOrEqualToViolationFactory
import io.github.kverify.violation.factory.classbased.set.comparable.ComparableLessThanViolationFactory
import io.github.kverify.violation.factory.classbased.set.comparable.ComparableNotBetweenViolationFactory
import io.github.kverify.violation.factory.classbased.set.comparable.ComparableNotEqualToViolationFactory
import io.github.kverify.violation.factory.provider.ComparableViolationFactoryProvider
import io.github.kverify.violation.set.provider.ComparableViolationProvider

@Suppress("TooManyFunctions")
public class ClassBasedComparableViolationFactorySet(
    public val comparableViolationProvider: ComparableViolationProvider = ComparableViolationProvider.Default,
) : ComparableViolationFactoryProvider {
    override fun <T : Comparable<T>> between(range: ClosedRange<T>): ViolationFactory<T> =
        ComparableBetweenViolationFactory(
            range = range,
            comparableViolationProvider = comparableViolationProvider,
        )

    override fun <T : Comparable<T>> equalTo(other: T): ViolationFactory<T> =
        ComparableEqualToViolationFactory(
            other = other,
            comparableViolationProvider = comparableViolationProvider,
        )

    override fun <T : Comparable<T>> greaterThanOrEqualTo(other: T): ViolationFactory<T> =
        ComparableGreaterThanOrEqualToViolationFactory(
            other = other,
            comparableViolationProvider = comparableViolationProvider,
        )

    override fun <T : Comparable<T>> greaterThan(other: T): ViolationFactory<T> =
        ComparableGreaterThanViolationFactory(
            other = other,
            comparableViolationProvider = comparableViolationProvider,
        )

    override fun <T : Comparable<T>> lessThanOrEqualTo(other: T): ViolationFactory<T> =
        ComparableLessThanOrEqualToViolationFactory(
            other = other,
            comparableViolationProvider = comparableViolationProvider,
        )

    override fun <T : Comparable<T>> lessThan(other: T): ViolationFactory<T> =
        ComparableLessThanViolationFactory(
            other = other,
            comparableViolationProvider = comparableViolationProvider,
        )

    override fun <T : Comparable<T>> notBetween(range: ClosedRange<T>): ViolationFactory<T> =
        ComparableNotBetweenViolationFactory(
            range = range,
            comparableViolationProvider = comparableViolationProvider,
        )

    override fun <T : Comparable<T>> notEqualTo(other: T): ViolationFactory<T> =
        ComparableNotEqualToViolationFactory(
            other = other,
            comparableViolationProvider = comparableViolationProvider,
        )
}
