package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public class StringMatchesViolation(
    override val value: String,
    public val regex: Regex,
    override val name: String?,
    override val reason: String =
        StringViolationLocalizationProvider.Default.matches(
            value = value,
            regex = regex,
            name = name,
        ),
) : TypedStringViolation

@Suppress("NOTHING_TO_INLINE")
public inline fun StringMatchesViolation(
    value: String,
    stringRegex: String,
    name: String? = null,
): StringMatchesViolation =
    StringMatchesViolation(
        value = value,
        regex = stringRegex.toRegex(),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringMatchesViolation(
    value: String,
    stringRegex: String,
    name: String? = null,
    reason: String,
): StringMatchesViolation =
    StringMatchesViolation(
        value = value,
        regex = stringRegex.toRegex(),
        name = name,
        reason = reason,
    )
