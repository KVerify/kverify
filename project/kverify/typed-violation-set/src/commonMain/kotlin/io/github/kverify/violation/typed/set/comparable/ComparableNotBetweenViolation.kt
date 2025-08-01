package io.github.kverify.violation.typed.set.comparable

import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation
import io.github.kverify.violation.typed.set.comparable.ComparableNotBetweenViolation

public data class ComparableNotBetweenViolation<T : Comparable<T>>(
    override val value: T,
    val range: ClosedRange<T>,
    override val name: String? = null,
    override val reason: String =
        ComparableViolationLocalizationProvider.Default.notBetween(
            value = value,
            range = range,
            name = name,
        ),
) : TypedViolation<T>

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableNotBetweenViolation(
    value: T,
    min: T,
    max: T,
    name: String? = null,
): ComparableNotBetweenViolation<T> =
    ComparableNotBetweenViolation(
        value = value,
        range = min..max,
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableNotBetweenViolation(
    value: T,
    min: T,
    max: T,
    name: String? = null,
    reason: String,
): ComparableNotBetweenViolation<T> =
    ComparableNotBetweenViolation(
        value = value,
        range = min..max,
        name = name,
        reason = reason,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableNotBetweenViolation(
    value: T,
    range: OpenEndRange<T>,
    name: String? = null,
): ComparableNotBetweenViolation<T> =
    ComparableNotBetweenViolation(
        value = value,
        range = range.start..range.endExclusive,
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> ComparableNotBetweenViolation(
    value: T,
    range: OpenEndRange<T>,
    name: String? = null,
    reason: String,
): ComparableNotBetweenViolation<T> =
    ComparableNotBetweenViolation(
        value = value,
        range = range.start..range.endExclusive,
        name = name,
        reason = reason,
    )
