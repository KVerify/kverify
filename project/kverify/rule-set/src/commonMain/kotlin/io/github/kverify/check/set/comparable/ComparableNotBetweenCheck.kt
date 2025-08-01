package io.github.kverify.check.set.comparable

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class ComparableNotBetweenCheck<T : Comparable<T>>(
    public val range: ClosedRange<T>,
) : ValidationCheck<T> {
    override fun isValid(value: T): Boolean = value !in range
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableNotBetweenCheck(
    min: T,
    max: T,
): ComparableNotBetweenCheck<T> =
    ComparableNotBetweenCheck(
        range = min..max,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableNotBetweenCheck(range: OpenEndRange<T>): ComparableNotBetweenCheck<T> =
    ComparableNotBetweenCheck(
        range = range.start..range.endExclusive,
    )
