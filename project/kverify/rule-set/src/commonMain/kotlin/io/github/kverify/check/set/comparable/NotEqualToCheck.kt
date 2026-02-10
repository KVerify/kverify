package io.github.kverify.check.set.comparable

import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationScope

public class NotEqualToCheck<T : Comparable<T>>(
    public val forbidden: T,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value != forbidden
}
