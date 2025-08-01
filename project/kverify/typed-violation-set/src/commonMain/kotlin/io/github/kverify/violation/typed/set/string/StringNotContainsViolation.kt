package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public data class StringNotContainsViolation(
    override val value: String,
    val substring: String,
    val ignoreCase: Boolean = false,
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
public inline fun StringNotContainsViolation(
    value: String,
    substring: Char,
    ignoreCase: Boolean = false,
    name: String? = null,
): StringNotContainsViolation =
    StringNotContainsViolation(
        value = value,
        substring = substring.toString(),
        ignoreCase = ignoreCase,
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringNotContainsViolation(
    value: String,
    substring: Char,
    ignoreCase: Boolean = false,
    name: String? = null,
    reason: String,
): StringNotContainsViolation =
    StringNotContainsViolation(
        value = value,
        substring = substring.toString(),
        ignoreCase = ignoreCase,
        name = name,
        reason = reason,
    )
