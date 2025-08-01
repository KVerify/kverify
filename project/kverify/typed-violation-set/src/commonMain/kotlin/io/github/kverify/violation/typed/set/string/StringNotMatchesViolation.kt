package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public class StringNotMatchesViolation(
    override val value: String,
    public val regex: Regex,
    override val name: String? = null,
    override val reason: String =
        StringViolationLocalizationProvider.Default.notMatches(
            value = value,
            regex = regex,
            name = name,
        ),
) : TypedStringViolation

@Suppress("NOTHING_TO_INLINE")
public inline fun StringNotMatchesViolation(
    value: String,
    stringRegex: String,
    name: String? = null,
): StringNotMatchesViolation =
    StringNotMatchesViolation(
        value = value,
        regex = stringRegex.toRegex(),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringNotMatchesViolation(
    value: String,
    stringRegex: String,
    name: String? = null,
    reason: String,
): StringNotMatchesViolation =
    StringNotMatchesViolation(
        value = value,
        regex = stringRegex.toRegex(),
        name = name,
        reason = reason,
    )
