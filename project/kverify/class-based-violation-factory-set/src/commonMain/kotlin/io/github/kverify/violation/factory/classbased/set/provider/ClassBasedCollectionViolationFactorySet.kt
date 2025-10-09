package io.github.kverify.violation.factory.classbased.set.provider

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.violation.factory.classbased.set.collection.CollectionContainsAllViolationFactory
import io.github.kverify.violation.factory.classbased.set.collection.CollectionContainsNoneViolationFactory
import io.github.kverify.violation.factory.classbased.set.collection.CollectionContainsOnlyViolationFactory
import io.github.kverify.violation.factory.classbased.set.collection.CollectionContainsViolationFactory
import io.github.kverify.violation.factory.classbased.set.collection.CollectionDistinctViolationFactory
import io.github.kverify.violation.factory.classbased.set.collection.CollectionMaxSizeViolationFactory
import io.github.kverify.violation.factory.classbased.set.collection.CollectionMinSizeViolationFactory
import io.github.kverify.violation.factory.classbased.set.collection.CollectionNotContainsViolationFactory
import io.github.kverify.violation.factory.classbased.set.collection.CollectionNotEmptyViolationFactory
import io.github.kverify.violation.factory.classbased.set.collection.CollectionNotOfSizeViolationFactory
import io.github.kverify.violation.factory.classbased.set.collection.CollectionOfSizeViolationFactory
import io.github.kverify.violation.factory.classbased.set.collection.CollectionSizeBetweenViolationFactory
import io.github.kverify.violation.factory.classbased.set.collection.CollectionSizeNotBetweenViolationFactory
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider
import io.github.kverify.violation.set.provider.CollectionViolationProvider

@Suppress("TooManyFunctions")
public class ClassBasedCollectionViolationFactorySet(
    public val collectionViolationProvider: CollectionViolationProvider = CollectionViolationProvider.Default,
) : CollectionViolationFactoryProvider {
    override fun <E, C : Collection<E>> containsAll(elements: Collection<E>): ViolationFactory<C> =
        CollectionContainsAllViolationFactory(
            elements = elements,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <E, C : Collection<E>> containsNone(elements: Collection<E>): ViolationFactory<C> =
        CollectionContainsNoneViolationFactory(
            elements = elements,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <E, C : Collection<E>> containsOnly(elements: Collection<E>): ViolationFactory<C> =
        CollectionContainsOnlyViolationFactory(
            elements = elements,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <E, C : Collection<E>> contains(element: E): ViolationFactory<C> =
        CollectionContainsViolationFactory(
            element = element,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <C : Collection<*>> distinct(): ViolationFactory<C> = CollectionDistinctViolationFactory()

    override fun <C : Collection<*>> maxSize(maxSize: Int): ViolationFactory<C> =
        CollectionMaxSizeViolationFactory(
            maxSize = maxSize,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <C : Collection<*>> minSize(minSize: Int): ViolationFactory<C> =
        CollectionMinSizeViolationFactory(
            minSize = minSize,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <E, C : Collection<E>> notContains(element: E): ViolationFactory<C> =
        CollectionNotContainsViolationFactory(
            element = element,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <C : Collection<*>> notEmpty(): ViolationFactory<C> = CollectionNotEmptyViolationFactory()

    override fun <C : Collection<*>> notOfSize(size: Int): ViolationFactory<C> =
        CollectionNotOfSizeViolationFactory(
            size = size,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <C : Collection<*>> ofSize(size: Int): ViolationFactory<C> =
        CollectionOfSizeViolationFactory(
            size = size,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <C : Collection<*>> sizeBetween(sizeRange: IntRange): ViolationFactory<C> =
        CollectionSizeBetweenViolationFactory(
            sizeRange = sizeRange,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <C : Collection<*>> sizeNotBetween(sizeRange: IntRange): ViolationFactory<C> =
        CollectionSizeNotBetweenViolationFactory(
            sizeRange = sizeRange,
            collectionViolationProvider = collectionViolationProvider,
        )
}
