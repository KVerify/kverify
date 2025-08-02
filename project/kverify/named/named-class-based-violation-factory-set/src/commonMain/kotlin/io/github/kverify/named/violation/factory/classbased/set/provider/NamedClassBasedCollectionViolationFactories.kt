package io.github.kverify.named.violation.factory.classbased.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.collection.NamedCollectionContainsAllViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.collection.NamedCollectionContainsNoneViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.collection.NamedCollectionContainsOnlyViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.collection.NamedCollectionContainsViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.collection.NamedCollectionDistinctViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.collection.NamedCollectionMaxSizeViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.collection.NamedCollectionMinSizeViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.collection.NamedCollectionNotContainsViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.collection.NamedCollectionNotEmptyViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.collection.NamedCollectionNotOfSizeViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.collection.NamedCollectionOfSizeViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.collection.NamedCollectionSizeBetweenViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.collection.NamedCollectionSizeNotBetweenViolationFactory
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider
import io.github.kverify.violation.set.provider.CollectionViolationProvider

public class NamedClassBasedCollectionViolationFactories(
    public val collectionViolationProvider: CollectionViolationProvider = CollectionViolationProvider.Default,
) : NamedCollectionViolationFactoryProvider {
    override fun <E, C : Collection<E>> namedContainsAll(elements: Collection<E>): NamedViolationFactory<C> =
        NamedCollectionContainsAllViolationFactory(
            elements = elements,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <E, C : Collection<E>> namedContainsNone(elements: Collection<E>): NamedViolationFactory<C> =
        NamedCollectionContainsNoneViolationFactory(
            elements = elements,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <E, C : Collection<E>> namedContainsOnly(elements: Collection<E>): NamedViolationFactory<C> =
        NamedCollectionContainsOnlyViolationFactory(
            elements = elements,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <E, C : Collection<E>> namedContains(element: E): NamedViolationFactory<C> =
        NamedCollectionContainsViolationFactory(
            element = element,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <C : Collection<*>> namedDistinct(): NamedViolationFactory<C> =
        NamedCollectionDistinctViolationFactory(
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <C : Collection<*>> namedMaxSize(maxSize: Int): NamedViolationFactory<C> =
        NamedCollectionMaxSizeViolationFactory(
            maxSize = maxSize,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <C : Collection<*>> namedMinSize(minSize: Int): NamedViolationFactory<C> =
        NamedCollectionMinSizeViolationFactory(
            minSize = minSize,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <E, C : Collection<E>> namedNotContains(element: E): NamedViolationFactory<C> =
        NamedCollectionNotContainsViolationFactory(
            element = element,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <C : Collection<*>> namedNotEmpty(): NamedViolationFactory<C> =
        NamedCollectionNotEmptyViolationFactory(
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <C : Collection<*>> namedNotOfSize(size: Int): NamedViolationFactory<C> =
        NamedCollectionNotOfSizeViolationFactory(
            size = size,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <C : Collection<*>> namedOfSize(size: Int): NamedViolationFactory<C> =
        NamedCollectionOfSizeViolationFactory(
            size = size,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <C : Collection<*>> namedSizeBetween(sizeRange: IntRange): NamedViolationFactory<C> =
        NamedCollectionSizeBetweenViolationFactory(
            sizeRange = sizeRange,
            collectionViolationProvider = collectionViolationProvider,
        )

    override fun <C : Collection<*>> namedSizeNotBetween(sizeRange: IntRange): NamedViolationFactory<C> =
        NamedCollectionSizeNotBetweenViolationFactory(
            sizeRange = sizeRange,
            collectionViolationProvider = collectionViolationProvider,
        )
}
