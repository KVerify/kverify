package io.github.kverify.check.set.string

import io.github.kverify.core.check.ValidationCheck

public object StringNotEmptyCheck : ValidationCheck<String> {
    override fun isValid(value: String): Boolean = value.isNotEmpty()
}
