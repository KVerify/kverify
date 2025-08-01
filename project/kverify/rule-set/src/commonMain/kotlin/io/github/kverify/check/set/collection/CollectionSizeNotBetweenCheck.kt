package io.github.kverify.check.set.collection

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class CollectionSizeNotBetweenCheck(
    public val sizeRange: IntRange,
) : ValidationCheck<Collection<*>> {
    override fun isValid(value: Collection<*>): Boolean = value.size !in sizeRange
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionSizeNotBetweenCheck(
    minSize: Int,
    maxSize: Int,
): CollectionSizeNotBetweenCheck =
    CollectionSizeNotBetweenCheck(
        sizeRange = minSize..maxSize,
    )
