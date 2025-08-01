package io.github.kverify.violation.typed.set.string

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

public class StringLengthBetweenViolation(
    override val value: String,
    public val lengthRange: IntRange,
    override val name: String? = null,
    override val reason: String =
        StringViolationLocalizationProvider.Default.lengthBetween(
            value = value,
            lengthRange = lengthRange,
            name = name,
        ),
) : TypedStringViolation

@Suppress("NOTHING_TO_INLINE")
public inline fun StringLengthBetweenViolation(
    value: String,
    minLength: Int,
    maxLength: Int,
    name: String? = null,
    reason: String,
): StringLengthBetweenViolation =
    StringLengthBetweenViolation(
        value = value,
        lengthRange = minLength..maxLength,
        name = name,
        reason = reason,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringLengthBetweenViolation(
    value: String,
    minLength: Int,
    maxLength: Int,
    name: String? = null,
): StringLengthBetweenViolation =
    StringLengthBetweenViolation(
        value = value,
        lengthRange = minLength..maxLength,
        name = name,
    )
