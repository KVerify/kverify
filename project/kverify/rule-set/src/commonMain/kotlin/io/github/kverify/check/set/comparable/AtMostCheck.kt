package io.github.kverify.check.set.comparable

import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.scope.ValidationScope

public class AtMostCheck<T : Comparable<T>>(
    public val max: T,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value <= max
}
