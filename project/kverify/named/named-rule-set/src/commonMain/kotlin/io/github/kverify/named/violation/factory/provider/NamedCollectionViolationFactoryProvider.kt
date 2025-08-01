@file:Suppress("TooManyFunctions")

package io.github.kverify.named.violation.factory.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.violation.set.provider.CollectionViolationProvider

public interface NamedCollectionViolationFactoryProvider {
    public fun <E, C : Collection<E>> namedContainsAll(elements: Collection<E>): NamedViolationFactory<C>

    public fun <E, C : Collection<E>> namedContainsNone(elements: Collection<E>): NamedViolationFactory<C>

    public fun <E, C : Collection<E>> namedContainsOnly(elements: Collection<E>): NamedViolationFactory<C>

    public fun <E, C : Collection<E>> namedContains(element: E): NamedViolationFactory<C>

    public fun <C : Collection<*>> namedDistinct(): NamedViolationFactory<C>

    public fun <C : Collection<*>> namedMaxSize(maxSize: Int): NamedViolationFactory<C>

    public fun <C : Collection<*>> namedMinSize(minSize: Int): NamedViolationFactory<C>

    public fun <E, C : Collection<E>> namedNotContains(element: E): NamedViolationFactory<C>

    public fun <C : Collection<*>> namedNotEmpty(): NamedViolationFactory<C>

    public fun <C : Collection<*>> namedNotOfSize(size: Int): NamedViolationFactory<C>

    public fun <C : Collection<*>> namedOfSize(size: Int): NamedViolationFactory<C>

    public fun <C : Collection<*>> namedSizeBetween(sizeRange: IntRange): NamedViolationFactory<C>

    public fun <C : Collection<*>> namedSizeNotBetween(sizeRange: IntRange): NamedViolationFactory<C>

    public companion object {
        public val Default: NamedCollectionViolationFactoryProvider = NamedCollectionViolationFactories()
    }
}

public class NamedCollectionViolationFactories(
    public val collectionViolationProvider: CollectionViolationProvider = CollectionViolationProvider.Default,
) : NamedCollectionViolationFactoryProvider {
    override fun <E, C : Collection<E>> namedContainsAll(elements: Collection<E>): NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.containsAll(
                value = value,
                elements = elements,
                name = name,
            )
        }

    override fun <E, C : Collection<E>> namedContainsNone(elements: Collection<E>): NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.containsNone(
                value = value,
                elements = elements,
                name = name,
            )
        }

    override fun <E, C : Collection<E>> namedContainsOnly(elements: Collection<E>): NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.containsOnly(
                value = value,
                elements = elements,
                name = name,
            )
        }

    override fun <E, C : Collection<E>> namedContains(element: E): NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.contains(
                value = value,
                element = element,
                name = name,
            )
        }

    override fun <C : Collection<*>> namedDistinct(): NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.distinct(
                value = value,
                name = name,
            )
        }

    override fun <C : Collection<*>> namedMaxSize(maxSize: Int): NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.maxSize(
                value = value,
                maxSize = maxSize,
                name = name,
            )
        }

    override fun <C : Collection<*>> namedMinSize(minSize: Int): NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.minSize(
                value = value,
                minSize = minSize,
                name = name,
            )
        }

    override fun <E, C : Collection<E>> namedNotContains(element: E): NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.notContains(
                value = value,
                element = element,
                name = name,
            )
        }

    override fun <C : Collection<*>> namedNotEmpty(): NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.notEmpty(
                value = value,
                name = name,
            )
        }

    override fun <C : Collection<*>> namedNotOfSize(size: Int): NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.notOfSize(
                value = value,
                size = size,
                name = name,
            )
        }

    override fun <C : Collection<*>> namedOfSize(size: Int): NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.ofSize(
                value = value,
                size = size,
                name = name,
            )
        }

    override fun <C : Collection<*>> namedSizeBetween(sizeRange: IntRange): NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.sizeBetween(
                value = value,
                sizeRange = sizeRange,
                name = name,
            )
        }

    override fun <C : Collection<*>> namedSizeNotBetween(sizeRange: IntRange): NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.sizeNotBetween(
                value = value,
                sizeRange = sizeRange,
                name = name,
            )
        }
}
