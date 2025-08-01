package io.github.kverify.violation.factory.classbased.set.comparable

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.ComparableViolationProvider

public class ComparableNotBetweenViolationFactory<T : Comparable<T>>(
    public val range: ClosedRange<T>,
    public val comparableViolationProvider: ComparableViolationProvider = ComparableViolationProvider.Default,
) : ViolationFactory<T> {
    override fun createViolation(value: T): Violation =
        comparableViolationProvider.notBetween(
            value = value,
            range = range,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableNotBetweenViolationFactory(
    range: OpenEndRange<T>,
    comparableViolationProvider: ComparableViolationProvider,
): ComparableNotBetweenViolationFactory<T> =
    ComparableNotBetweenViolationFactory(
        range = range.start..range.endExclusive,
        comparableViolationProvider = comparableViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableNotBetweenViolationFactory(
    range: OpenEndRange<T>,
): ComparableNotBetweenViolationFactory<T> =
    ComparableNotBetweenViolationFactory(
        range = range.start..range.endExclusive,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableNotBetweenViolationFactory(
    min: T,
    max: T,
    comparableViolationProvider: ComparableViolationProvider,
): ComparableNotBetweenViolationFactory<T> =
    ComparableNotBetweenViolationFactory(
        range = min..max,
        comparableViolationProvider = comparableViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableNotBetweenViolationFactory(
    min: T,
    max: T,
): ComparableNotBetweenViolationFactory<T> =
    ComparableNotBetweenViolationFactory(
        range = min..max,
    )
