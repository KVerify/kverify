package io.github.kverify.check.set.string

import io.github.kverify.core.rule.predicate.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class StringContainsNoneCheck(
    public val chars: Iterable<Char>,
) : ValidationCheck<String> {
    override fun isValid(value: String): Boolean = chars.none { it in value }
}
