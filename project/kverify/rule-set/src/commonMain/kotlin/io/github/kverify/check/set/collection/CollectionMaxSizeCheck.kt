package io.github.kverify.check.set.collection

import io.github.kverify.core.check.ValidationCheck

public class CollectionMaxSizeCheck(
    public val maxSize: Int,
) : ValidationCheck<Collection<*>> {
    override fun isValid(value: Collection<*>): Boolean = value.size <= maxSize
}
