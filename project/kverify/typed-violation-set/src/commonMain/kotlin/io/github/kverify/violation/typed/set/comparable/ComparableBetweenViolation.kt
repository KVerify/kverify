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
