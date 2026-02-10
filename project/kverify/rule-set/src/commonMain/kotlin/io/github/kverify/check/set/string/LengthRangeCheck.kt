package io.github.kverify.check.set.string

import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationScope

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
