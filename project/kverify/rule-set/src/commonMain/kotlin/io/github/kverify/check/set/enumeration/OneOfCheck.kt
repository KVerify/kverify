package io.github.kverify.check.set.enumeration

import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.scope.ValidationScope

public class OneOfCheck<T>(
    public val allowed: Set<T>,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value in allowed
}
