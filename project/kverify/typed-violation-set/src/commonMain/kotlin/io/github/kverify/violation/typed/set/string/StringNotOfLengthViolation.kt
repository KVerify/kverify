package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public data class StringNotOfLengthViolation(
    override val value: String,
    val length: Int,
    override val name: String? = null,
    override val reason: String =
        StringViolationLocalizationProvider.Default.ofLength(
            value = value,
            length = length,
            name = name,
        ),
) : TypedStringViolation
