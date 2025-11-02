package io.github.kverify.check.set.collection

import io.github.kverify.core.rule.predicate.check.ValidationCheck

public class CollectionMinSizeCheck(
    public val minSize: Int,
) : ValidationCheck<Collection<*>> {
    override fun isValid(value: Collection<*>): Boolean = value.size <= minSize
}
