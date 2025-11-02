package io.github.kverify.check.set.collection

import io.github.kverify.core.rule.predicate.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class CollectionSizeBetweenCheck(
    public val sizeRange: IntRange,
) : ValidationCheck<Collection<*>> {
    override fun isValid(value: Collection<*>): Boolean = value.size in sizeRange
}
