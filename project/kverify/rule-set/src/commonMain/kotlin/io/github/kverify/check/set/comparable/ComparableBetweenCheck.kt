package io.github.kverify.check.set.comparable

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class ComparableBetweenCheck<T : Comparable<T>>(
    public val range: ClosedRange<T>,
) : ValidationCheck<T> {
    override fun isValid(value: T): Boolean = value in range
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableBetweenCheck(
    min: T,
    max: T,
): ComparableBetweenCheck<T> =
    ComparableBetweenCheck(
        range = min..max,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableBetweenCheck(range: OpenEndRange<T>): ComparableBetweenCheck<T> =
    ComparableBetweenCheck(
        range = range.start..range.endExclusive,
    )
