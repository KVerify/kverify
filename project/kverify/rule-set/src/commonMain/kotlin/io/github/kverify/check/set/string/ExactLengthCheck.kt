package io.github.kverify.check.set.string

import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationScope

public class ExactLengthCheck(
    public val length: Int,
) : ValidationCheck<String> {
    override fun isValid(
        scope: ValidationScope,
        value: String,
    ): Boolean = value.length == length
}
