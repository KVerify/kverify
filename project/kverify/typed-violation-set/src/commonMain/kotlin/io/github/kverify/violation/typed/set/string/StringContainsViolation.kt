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
