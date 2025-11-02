package io.github.kverify.check.set.string

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class StringContainsOnlyCheck(
    public val chars: Iterable<Char>,
) : ValidationCheck<String> {
    override fun isValid(value: String): Boolean = value.all { it in chars }
}
