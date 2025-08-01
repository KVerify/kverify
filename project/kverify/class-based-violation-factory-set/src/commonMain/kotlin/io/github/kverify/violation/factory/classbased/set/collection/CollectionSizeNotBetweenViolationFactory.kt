package io.github.kverify.violation.factory.classbased.set.collection

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.CollectionViolationProvider

public class CollectionSizeNotBetweenViolationFactory<C : Collection<*>>(
    public val sizeRange: IntRange,
    public val collectionViolationProvider: CollectionViolationProvider = CollectionViolationProvider.Default,
) : ViolationFactory<C> {
    override fun createViolation(value: C): Violation =
        collectionViolationProvider.sizeNotBetween(
            value = value,
            sizeRange = sizeRange,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionSizeNotBetweenViolationFactory(
    minSize: Int,
    maxSize: Int,
    collectionViolationProvider: CollectionViolationProvider,
): CollectionSizeNotBetweenViolationFactory<C> =
    CollectionSizeNotBetweenViolationFactory(
        sizeRange = minSize..maxSize,
        collectionViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionSizeNotBetweenViolationFactory(
    minSize: Int,
    maxSize: Int,
): CollectionSizeNotBetweenViolationFactory<C> =
    CollectionSizeNotBetweenViolationFactory(
        sizeRange = minSize..maxSize,
    )
