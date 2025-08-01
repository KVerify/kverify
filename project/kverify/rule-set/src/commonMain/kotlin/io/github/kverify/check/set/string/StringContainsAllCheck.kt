package io.github.kverify.check.set.string

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class StringContainsAllCheck(
    public val chars: Iterable<Char>,
) : ValidationCheck<String> {
    override fun isValid(value: String): Boolean = chars.all { it in value }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsAllCheck(vararg chars: Char): StringContainsAllCheck =
    StringContainsAllCheck(
        chars = chars.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsAllCheck(string: String): StringContainsAllCheck =
    StringContainsAllCheck(
        chars = string.asIterable(),
    )
