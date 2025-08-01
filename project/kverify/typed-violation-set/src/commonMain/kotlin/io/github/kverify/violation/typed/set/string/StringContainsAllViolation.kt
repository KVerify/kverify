package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public class StringContainsAllViolation(
    override val value: String,
    public val chars: Iterable<Char>,
    override val name: String? = null,
    override val reason: String =
        StringViolationLocalizationProvider.Default.containsAll(
            value = value,
            chars = chars,
            name = name,
        ),
) : TypedStringViolation

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsAllViolation(
    value: String,
    string: String,
    name: String? = null,
): StringContainsAllViolation =
    StringContainsAllViolation(
        value = value,
        chars = string.asIterable(),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsAllViolation(
    value: String,
    string: String,
    name: String? = null,
    reason: String,
): StringContainsAllViolation =
    StringContainsAllViolation(
        value = value,
        chars = string.asIterable(),
        name = name,
        reason = reason,
    )
