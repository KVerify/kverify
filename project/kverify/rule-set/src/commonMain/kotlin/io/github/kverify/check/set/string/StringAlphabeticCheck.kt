package io.github.kverify.check.set.string

import io.github.kverify.core.rule.predicate.check.ValidationCheck

public object StringAlphabeticCheck : ValidationCheck<String> {
    override fun isValid(value: String): Boolean = value.all { it.isLetter() }
}
