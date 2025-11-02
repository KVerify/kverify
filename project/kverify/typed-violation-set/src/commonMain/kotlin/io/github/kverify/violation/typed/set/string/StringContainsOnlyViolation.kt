package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public class StringContainsOnlyViolation(
    override val value: String,
    public val chars: Iterable<Char>,
    override val name: String? = null,
    override val reason: String =
        StringViolationLocalizationProvider.Default.containsOnly(
            value = value,
            chars = chars,
            name = name,
        ),
) : TypedStringViolation
