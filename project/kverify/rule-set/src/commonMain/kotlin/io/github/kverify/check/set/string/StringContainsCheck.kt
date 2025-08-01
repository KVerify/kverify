package io.github.kverify.check.set.string

import io.github.kverify.core.check.ValidationCheck

public class StringContainsCheck(
    public val substring: String,
    public val ignoreCase: Boolean = false,
) : ValidationCheck<String> {
    override fun isValid(value: String): Boolean = value.contains(substring, ignoreCase)
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsCheck(
    char: Char,
    ignoreCase: Boolean = false,
): StringContainsCheck =
    StringContainsCheck(
        substring = char.toString(),
        ignoreCase = ignoreCase,
    )
