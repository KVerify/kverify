package io.github.kverify.check.set.string

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class StringNotOfLengthCheck(
    public val length: Int,
) : ValidationCheck<String> {
    override fun isValid(value: String): Boolean = value.length != length
}
