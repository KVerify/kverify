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
