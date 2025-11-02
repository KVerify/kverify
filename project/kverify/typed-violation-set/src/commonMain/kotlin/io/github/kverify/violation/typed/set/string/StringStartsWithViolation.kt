package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public data class StringStartsWithViolation(
    override val value: String,
    val prefix: String,
    val ignoreCase: Boolean = false,
    override val name: String? = null,
    override val reason: String =
        StringViolationLocalizationProvider.Default.startsWith(
            value = value,
            prefix = prefix,
            ignoreCase = ignoreCase,
            name = name,
        ),
) : TypedStringViolation
