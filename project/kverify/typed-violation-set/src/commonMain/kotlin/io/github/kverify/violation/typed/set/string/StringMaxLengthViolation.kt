package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public data class StringMaxLengthViolation(
    override val value: String,
    val maxLength: Int,
    override val name: String? = null,
    override val reason: String =
        StringViolationLocalizationProvider.Default.maxLength(value, maxLength, name),
) : TypedStringViolation
