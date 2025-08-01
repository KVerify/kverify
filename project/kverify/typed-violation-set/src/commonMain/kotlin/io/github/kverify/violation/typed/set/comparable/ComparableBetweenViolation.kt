package io.github.kverify.violation.typed.set.comparable

import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation
import kotlin.jvm.JvmInline

public data class ComparableBetweenViolation<T : Comparable<T>>(
    override val value: T,
    val range: ClosedRange<T>,
    override val name: String? = null,
    override val reason: String =
        ComparableViolationLocalizationProvider.Default.between(
            value = value,
            range = range,
            name = name,
        ),
) : TypedViolation<T>

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableBetweenViolation(
    value: T,
    min: T,
    max: T,
    name: String? = null,
): ComparableBetweenViolation<T> =
    ComparableBetweenViolation(
        value = value,
        range = min..max,
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableBetweenViolation(
    value: T,
    min: T,
    max: T,
    name: String? = null,
    reason: String,
): ComparableBetweenViolation<T> =
    ComparableBetweenViolation(
        value = value,
        range = min..max,
        name = name,
        reason = reason,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableBetweenViolation(
    value: T,
    range: OpenEndRange<T>,
    name: String? = null,
): ComparableBetweenViolation<T> =
    ComparableBetweenViolation(
        value = value,
        range = range.start..range.endExclusive,
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableBetweenViolation(
    value: T,
    range: OpenEndRange<T>,
    name: String? = null,
    reason: String,
): ComparableBetweenViolation<T> =
    ComparableBetweenViolation(
        value = value,
        range = range.start..range.endExclusive,
        name = name,
        reason = reason,
    )
