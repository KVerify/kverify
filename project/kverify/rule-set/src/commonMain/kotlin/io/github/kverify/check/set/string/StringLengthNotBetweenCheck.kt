package io.github.kverify.check.set.string

import io.github.kverify.core.rule.predicate.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class StringLengthNotBetweenCheck(
    public val lengthRange: IntRange,
) : ValidationCheck<String> {
    override fun isValid(value: String): Boolean = value.length !in lengthRange
}
