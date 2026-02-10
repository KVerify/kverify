package io.github.kverify.check.set.string

import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationScope

public class PatternCheck(
    public val regex: Regex,
) : ValidationCheck<String> {
    override fun isValid(
        scope: ValidationScope,
        value: String,
    ): Boolean = value.matches(regex)
}
