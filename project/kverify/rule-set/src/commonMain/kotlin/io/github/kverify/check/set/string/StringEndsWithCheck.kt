package io.github.kverify.check.set.string

import io.github.kverify.core.rule.predicate.check.ValidationCheck

public class StringEndsWithCheck(
    public val suffix: String,
    public val ignoreCase: Boolean = false,
) : ValidationCheck<String> {
    override fun isValid(value: String): Boolean = value.endsWith(suffix, ignoreCase)
}
