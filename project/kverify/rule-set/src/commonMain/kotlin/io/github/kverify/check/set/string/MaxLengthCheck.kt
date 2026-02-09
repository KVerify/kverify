package io.github.kverify.check.set.string

import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.scope.ValidationScope

public class MaxLengthCheck(
    public val max: Int,
) : ValidationCheck<String> {
    override fun isValid(
        scope: ValidationScope,
        value: String,
    ): Boolean = value.length <= max
}
