package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public class StringContainsNoneViolation(
    override val value: String,
    public val chars: Iterable<Char>,
    override val name: String? = null,
    override val reason: String =
        StringViolationLocalizationProvider.Default.containsNone(
            value = value,
            chars = chars,
            name = name,
        ),
) : TypedStringViolation

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsNoneViolation(
    value: String,
    string: String,
    name: String? = null,
): StringContainsNoneViolation =
    StringContainsNoneViolation(
        value = value,
        chars = string.asIterable(),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsNoneViolation(
    value: String,
    string: String,
    name: String? = null,
    reason: String,
): StringContainsNoneViolation =
    StringContainsNoneViolation(
        value = value,
        chars = string.asIterable(),
        name = name,
        reason = reason,
    )
