@file:Suppress("TooManyFunctions")

package io.github.kverify.named.violation.factory.provider

import io.github.kverify.named.check.NamedViolationFactory

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
        public val Default: NamedCollectionViolationFactoryProvider = DefaultNamedCollectionViolationFactoryProvider()
    }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionViolationFactoryProvider.containsAll(element: E): NamedViolationFactory<C> =
    namedContainsAll(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionViolationFactoryProvider.namedContainsAll(
    vararg elements: E,
): NamedViolationFactory<C> =
    namedContainsAll(
        elements = elements.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionViolationFactoryProvider.namedContainsNone(element: E): NamedViolationFactory<C> =
    namedContainsNone(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionViolationFactoryProvider.namedContainsNone(
    vararg elements: E,
): NamedViolationFactory<C> =
    namedContainsNone(
        elements = elements.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionViolationFactoryProvider.namedContainsOnly(element: E): NamedViolationFactory<C> =
    namedContainsOnly(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionViolationFactoryProvider.namedContainsOnly(
    vararg elements: E,
): NamedViolationFactory<C> =
    namedContainsOnly(
        elements = elements.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionViolationFactoryProvider.namedSizeBetween(
    minSize: Int,
    maxSize: Int,
): NamedViolationFactory<C> =
    namedSizeBetween(
        sizeRange = minSize..maxSize,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionViolationFactoryProvider.namedSizeNotBetween(
    minSize: Int,
    maxSize: Int,
): NamedViolationFactory<C> =
    namedSizeNotBetween(
        sizeRange = minSize..maxSize,
    )
