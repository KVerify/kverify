package io.github.kverify.violation.factory.provider

import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.set.provider.CollectionViolationProvider
import io.github.kverify.violation.set.provider.DefaultCollectionViolationProvider

@Suppress("TooManyFunctions")
public class DefaultCollectionViolationFactoryProvider(
    private val collectionViolationProvider: CollectionViolationProvider = CollectionViolationProvider.Default,
) : CollectionViolationFactoryProvider {
    public constructor(
        collectionViolationLocalizationProvider: CollectionViolationLocalizationProvider,
    ) : this(
        collectionViolationProvider =
            DefaultCollectionViolationProvider(
                collectionViolationLocalizationProvider = collectionViolationLocalizationProvider,
            ),
    )

    override fun <E, C : Collection<E>> containsAll(elements: Collection<E>): ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.containsAll(
                value = value,
                elements = elements,
            )
        }

    override fun <E, C : Collection<E>> containsNone(elements: Collection<E>): ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.containsNone(
                value = value,
                elements = elements,
            )
        }

    override fun <E, C : Collection<E>> containsOnly(elements: Collection<E>): ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.containsOnly(
                value = value,
                elements = elements,
            )
        }

    override fun <E, C : Collection<E>> contains(element: E): ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.contains(
                value = value,
                element = element,
            )
        }

    override fun <C : Collection<*>> distinct(): ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.distinct(
                value = value,
            )
        }

    override fun <C : Collection<*>> maxSize(maxSize: Int): ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.maxSize(
                value = value,
                maxSize = maxSize,
            )
        }

    override fun <C : Collection<*>> minSize(minSize: Int): ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.minSize(
                value = value,
                minSize = minSize,
            )
        }

    override fun <E, C : Collection<E>> notContains(element: E): ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.notContains(
                value = value,
                element = element,
            )
        }

    override fun <C : Collection<*>> notEmpty(): ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.notEmpty(
                value = value,
            )
        }

    override fun <C : Collection<*>> notOfSize(size: Int): ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.notOfSize(
                value = value,
                size = size,
            )
        }

    override fun <C : Collection<*>> ofSize(size: Int): ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.ofSize(
                value = value,
                size = size,
            )
        }

    override fun <C : Collection<*>> sizeBetween(sizeRange: IntRange): ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.sizeBetween(
                value = value,
                sizeRange = sizeRange,
            )
        }

    override fun <C : Collection<*>> sizeNotBetween(sizeRange: IntRange): ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.sizeNotBetween(
                value = value,
                sizeRange = sizeRange,
            )
        }
}
