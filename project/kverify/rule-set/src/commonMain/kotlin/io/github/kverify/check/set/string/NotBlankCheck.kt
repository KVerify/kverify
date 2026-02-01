package io.github.kverify.check.set.string

import io.github.kverify.core.rule.predicate.check.ValidationCheck
import io.github.kverify.core.scope.ValidationScope

public object NotBlankCheck : ValidationCheck<String> {
    override fun isValid(
        scope: ValidationScope,
        value: String,
    ): Boolean = value.isNotBlank()
}
