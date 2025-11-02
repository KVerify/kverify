package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public class StringLengthNotBetweenViolation(
    override val value: String,
    public val lengthRange: IntRange,
    override val name: String? = null,
    override val reason: String =
        StringViolationLocalizationProvider.Default.lengthNotBetween(
            value = value,
            lengthRange = lengthRange,
            name = name,
        ),
) : TypedStringViolation
