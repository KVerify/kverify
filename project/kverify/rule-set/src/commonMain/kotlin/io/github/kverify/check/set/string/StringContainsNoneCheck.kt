package io.github.kverify.check.set.string

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class StringContainsNoneCheck(
    public val chars: Iterable<Char>,
) : ValidationCheck<String> {
    override fun isValid(value: String): Boolean = chars.none { it in value }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsNoneCheck(vararg chars: Char): StringContainsNoneCheck =
    StringContainsNoneCheck(
        chars = chars.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsNoneCheck(string: String): StringContainsNoneCheck =
    StringContainsNoneCheck(
        chars = string.asIterable(),
    )
