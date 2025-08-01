package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public data class StringMinLengthViolation(
    override val value: String,
    val minLength: Int,
    override val name: String? = null,
    override val reason: String =
        StringViolationLocalizationProvider.Default.minLength(
            value = value,
            minLength = minLength,
            name = name,
        ),
) : TypedStringViolation
