package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public data class StringNumericViolation(
    override val value: String,
    override val name: String? = null,
    override val reason: String =
        StringViolationLocalizationProvider.Default.numeric(
            value = value,
            name = name,
        ),
) : TypedStringViolation
