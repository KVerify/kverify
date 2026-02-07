package io.github.kverify.check.set.enumeration

import io.github.kverify.core.rule.predicate.check.ValidationCheck
import io.github.kverify.core.scope.ValidationScope

public class NoneOfCheck<T>(
    public val forbidden: Set<T>,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value !in forbidden
}
