package io.github.kverify.check.set.string

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class StringMatchesCheck(
    public val regex: Regex,
) : ValidationCheck<String> {
    override fun isValid(value: String): Boolean = value.matches(regex)
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringMatchesCheck(stringRegex: String): StringMatchesCheck =
    StringMatchesCheck(
        regex = stringRegex.toRegex(),
    )
