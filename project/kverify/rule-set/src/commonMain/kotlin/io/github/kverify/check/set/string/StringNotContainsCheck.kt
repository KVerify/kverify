package io.github.kverify.check.set.string

import io.github.kverify.core.check.ValidationCheck

public class StringNotContainsCheck(
    public val substring: String,
    public val ignoreCase: Boolean = false,
) : ValidationCheck<String> {
    override fun isValid(value: String): Boolean = !value.contains(substring, ignoreCase)
}
