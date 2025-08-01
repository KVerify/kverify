package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public class StringContainsViolation(
    override val value: String,
    public val substring: String,
    public val ignoreCase: Boolean = false,
    override val name: String? = null,
    override val reason: String =
        StringViolationLocalizationProvider.Default.contains(
            value = value,
            substring = substring,
            ignoreCase = ignoreCase,
            name = name,
        ),
) : TypedStringViolation

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsViolation(
    value: String,
    substring: Char,
    ignoreCase: Boolean = false,
    name: String? = null,
): StringContainsViolation =
    StringContainsViolation(
        value = value,
        substring = substring.toString(),
        ignoreCase = ignoreCase,
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsViolation(
    value: String,
    substring: Char,
    ignoreCase: Boolean = false,
    name: String? = null,
    reason: String,
): StringContainsViolation =
    StringContainsViolation(
        value = value,
        substring = substring.toString(),
        ignoreCase = ignoreCase,
        name = name,
        reason = reason,
    )
