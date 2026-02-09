package io.github.kverify.check.set.string

import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.scope.ValidationScope

public class LengthRangeCheck(
    public val min: Int,
    public val max: Int,
) : ValidationCheck<String> {
    override fun isValid(
        scope: ValidationScope,
        value: String,
    ): Boolean {
        val length = value.length

        return (length >= min) && (length <= max)
    }
}
