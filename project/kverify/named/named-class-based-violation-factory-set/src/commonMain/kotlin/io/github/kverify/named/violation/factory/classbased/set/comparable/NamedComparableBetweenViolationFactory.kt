package io.github.kverify.named.violation.factory.classbased.set.comparable

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.ComparableViolationProvider

public class NamedComparableBetweenViolationFactory<T : Comparable<T>>(
    public val range: ClosedRange<T>,
    public val comparableViolationProvider: ComparableViolationProvider = ComparableViolationProvider.Default,
) : NamedViolationFactory<T> {
    override fun createViolation(value: NamedValue<T>): Violation =
        comparableViolationProvider.between(
            value = value.value,
            range = range,
            name = value.name,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> NamedComparableBetweenViolationFactory(
    range: OpenEndRange<T>,
    comparableViolationProvider: ComparableViolationProvider,
): NamedComparableBetweenViolationFactory<T> =
    NamedComparableBetweenViolationFactory(
        range = range.start..range.endExclusive,
        comparableViolationProvider = comparableViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> NamedComparableBetweenViolationFactory(
    range: OpenEndRange<T>,
): NamedComparableBetweenViolationFactory<T> =
    NamedComparableBetweenViolationFactory(
        range = range.start..range.endExclusive,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> NamedComparableBetweenViolationFactory(
    min: T,
    max: T,
    comparableViolationProvider: ComparableViolationProvider,
): NamedComparableBetweenViolationFactory<T> =
    NamedComparableBetweenViolationFactory(
        range = min..max,
        comparableViolationProvider = comparableViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> NamedComparableBetweenViolationFactory(
    min: T,
    max: T,
): NamedComparableBetweenViolationFactory<T> =
    NamedComparableBetweenViolationFactory(
        range = min..max,
    )
