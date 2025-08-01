package io.github.kverify.check.set.string

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class StringLengthNotBetweenCheck(
    public val lengthRange: IntRange,
) : ValidationCheck<String> {
    override fun isValid(value: String): Boolean = value.length !in lengthRange
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringLengthNotBetweenCheck(
    minLength: Int,
    maxLength: Int,
): StringLengthNotBetweenCheck =
    StringLengthNotBetweenCheck(
        lengthRange = minLength..maxLength,
    )
