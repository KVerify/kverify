package io.github.kverify.check.set.comparable

import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationScope

public class BetweenCheck<T : Comparable<T>>(
    public val min: T,
    public val max: T,
) : ValidationCheck<T> {
    @Suppress("ConvertTwoComparisonsToRangeCheck")
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value >= min && value <= max
}
