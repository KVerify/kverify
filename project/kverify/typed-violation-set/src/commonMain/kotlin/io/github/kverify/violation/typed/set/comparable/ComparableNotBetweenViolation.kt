package io.github.kverify.violation.typed.set.comparable

import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation

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
