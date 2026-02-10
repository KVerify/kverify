package io.github.kverify.check.set.collection

import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationScope

public class SizeRangeCheck(
    public val min: Int,
    public val max: Int,
) : ValidationCheck<Collection<*>> {
    @Suppress("ConvertTwoComparisonsToRangeCheck")
    override fun isValid(
        scope: ValidationScope,
        value: Collection<*>,
    ): Boolean = value.size >= min && value.size <= max
}
