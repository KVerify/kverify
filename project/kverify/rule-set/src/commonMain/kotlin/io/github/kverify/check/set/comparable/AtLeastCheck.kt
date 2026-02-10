package io.github.kverify.check.set.comparable

import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationScope

public class AtLeastCheck<T : Comparable<T>>(
    public val min: T,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value >= min
}
