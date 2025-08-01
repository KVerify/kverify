package io.github.kverify.violation.typed.set.comparable

import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation

public data class ComparableGreaterThanOrEqualToViolation<T : Comparable<T>>(
    override val value: T,
    val other: T,
    override val name: String? = null,
    override val reason: String =
        ComparableViolationLocalizationProvider.Default.greaterThanOrEqualTo(
            value = value,
            other = other,
            name = name,
        ),
) : TypedViolation<T>
