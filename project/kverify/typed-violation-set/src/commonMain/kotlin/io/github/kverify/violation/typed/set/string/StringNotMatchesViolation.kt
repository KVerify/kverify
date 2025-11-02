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
