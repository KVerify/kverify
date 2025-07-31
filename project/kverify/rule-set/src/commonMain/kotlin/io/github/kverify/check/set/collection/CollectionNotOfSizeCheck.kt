package io.github.kverify.check.set.collection

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class CollectionNotOfSizeCheck(
    public val size: Int,
) : ValidationCheck<Collection<*>> {
    override fun isValid(value: Collection<*>): Boolean = value.size != size
}
