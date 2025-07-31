package io.github.kverify.check.set.string

import io.github.kverify.core.check.ValidationCheck

public object StringAlphanumericCheck : ValidationCheck<String> {
    override fun isValid(value: String): Boolean = value.all { it.isLetterOrDigit() }
}
