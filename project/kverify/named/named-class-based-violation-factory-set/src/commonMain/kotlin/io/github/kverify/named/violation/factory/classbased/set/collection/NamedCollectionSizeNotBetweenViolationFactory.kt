package io.github.kverify.named.violation.factory.classbased.set.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.CollectionViolationProvider

public class NamedCollectionSizeNotBetweenViolationFactory<C : Collection<*>>(
    public val sizeRange: IntRange,
    public val collectionViolationProvider: CollectionViolationProvider = CollectionViolationProvider.Default,
) : NamedViolationFactory<C> {
    override fun createViolation(value: NamedValue<C>): Violation =
        collectionViolationProvider.sizeNotBetween(
            value = value.value,
            sizeRange = sizeRange,
            name = value.name,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionSizeNotBetweenViolationFactory(
    minSize: Int,
    maxSize: Int,
    collectionViolationProvider: CollectionViolationProvider,
): NamedCollectionSizeNotBetweenViolationFactory<C> =
    NamedCollectionSizeNotBetweenViolationFactory(
        sizeRange = minSize..maxSize,
        collectionViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionSizeNotBetweenViolationFactory(
    minSize: Int,
    maxSize: Int,
): NamedCollectionSizeNotBetweenViolationFactory<C> =
    NamedCollectionSizeNotBetweenViolationFactory(
        sizeRange = minSize..maxSize,
    )
