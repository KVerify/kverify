package io.github.kverify.check.set.comparable

import io.github.kverify.core.rule.predicate.check.ValidationCheck
import io.github.kverify.core.scope.ValidationScope

public class GreaterThanCheck<T : Comparable<T>>(
    public val min: T,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value > min
}
