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
