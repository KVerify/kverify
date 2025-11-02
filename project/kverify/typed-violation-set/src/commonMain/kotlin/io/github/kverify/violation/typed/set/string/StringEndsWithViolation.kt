package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public data class StringEndsWithViolation(
    override val value: String,
    val suffix: String,
    val ignoreCase: Boolean = false,
    override val name: String? = null,
    override val reason: String =
        StringViolationLocalizationProvider.Default.endsWith(
            value = value,
            suffix = suffix,
            ignoreCase = ignoreCase,
            name = name,
        ),
) : TypedStringViolation
