package io.github.kverify.violation.factory.classbased.set.collection

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.CollectionViolationProvider

public class CollectionSizeBetweenViolationFactory<C : Collection<*>>(
    public val sizeRange: IntRange,
    public val collectionViolationProvider: CollectionViolationProvider = CollectionViolationProvider.Default,
) : ViolationFactory<C> {
    override fun createViolation(value: C): Violation =
        collectionViolationProvider.sizeBetween(
            value = value,
            sizeRange = sizeRange,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionSizeBetweenViolationFactory(
    minSize: Int,
    maxSize: Int,
    collectionViolationProvider: CollectionViolationProvider,
): CollectionSizeBetweenViolationFactory<C> =
    CollectionSizeBetweenViolationFactory(
        sizeRange = minSize..maxSize,
        collectionViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionSizeBetweenViolationFactory(
    minSize: Int,
    maxSize: Int,
): CollectionSizeBetweenViolationFactory<C> =
    CollectionSizeBetweenViolationFactory(
        sizeRange = minSize..maxSize,
    )
