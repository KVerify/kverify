package io.github.kverify.violation.typed.set.comparable

import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation

public data class ComparableNotEqualToViolation<T : Comparable<T>>(
    override val value: T,
    val other: T,
    override val name: String? = null,
    override val reason: String =
        ComparableViolationLocalizationProvider.Default.notEqualTo(
            value = value,
            other = other,
            name = name,
        ),
) : TypedViolation<T>
