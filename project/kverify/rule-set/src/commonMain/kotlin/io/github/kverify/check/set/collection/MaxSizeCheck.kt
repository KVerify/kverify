package io.github.kverify.check.set.collection

import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationScope

public class MaxSizeCheck(
    public val max: Int,
) : ValidationCheck<Collection<*>> {
    override fun isValid(
        scope: ValidationScope,
        value: Collection<*>,
    ): Boolean = value.size <= max
}
