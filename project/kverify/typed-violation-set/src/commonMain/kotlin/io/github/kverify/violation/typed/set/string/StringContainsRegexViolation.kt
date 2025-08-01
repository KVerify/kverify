package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public class StringContainsRegexViolation(
    override val value: String,
    public val regex: Regex,
    override val name: String?,
    override val reason: String =
        StringViolationLocalizationProvider.Default.containsRegex(
            value = value,
            regex = regex,
            name = name,
        ),
) : TypedStringViolation

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsRegexViolation(
    value: String,
    stringRegex: String,
    name: String? = null,
): StringContainsRegexViolation =
    StringContainsRegexViolation(
        value = value,
        regex = stringRegex.toRegex(),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsRegexViolation(
    value: String,
    stringRegex: String,
    name: String? = null,
    reason: String,
): StringContainsRegexViolation =
    StringContainsRegexViolation(
        value = value,
        regex = stringRegex.toRegex(),
        name = name,
        reason = reason,
    )
