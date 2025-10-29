package io.github.kverify.violation.factory.provider

import io.github.kverify.core.check.ViolationFactory

@Suppress("TooManyFunctions")
public interface CollectionViolationFactoryProvider {
    public fun <E, C : Collection<E>> containsAll(elements: Collection<E>): ViolationFactory<C>

    public fun <E, C : Collection<E>> containsNone(elements: Collection<E>): ViolationFactory<C>

    public fun <E, C : Collection<E>> containsOnly(elements: Collection<E>): ViolationFactory<C>

    public fun <E, C : Collection<E>> contains(element: E): ViolationFactory<C>

    public fun <C : Collection<*>> distinct(): ViolationFactory<C>

    public fun <C : Collection<*>> maxSize(maxSize: Int): ViolationFactory<C>

    public fun <C : Collection<*>> minSize(minSize: Int): ViolationFactory<C>

    public fun <E, C : Collection<E>> notContains(element: E): ViolationFactory<C>

    public fun <C : Collection<*>> notEmpty(): ViolationFactory<C>

    public fun <C : Collection<*>> notOfSize(size: Int): ViolationFactory<C>

    public fun <C : Collection<*>> ofSize(size: Int): ViolationFactory<C>

    public fun <C : Collection<*>> sizeBetween(sizeRange: IntRange): ViolationFactory<C>

    public fun <C : Collection<*>> sizeNotBetween(sizeRange: IntRange): ViolationFactory<C>

    public companion object {
        public val Default: CollectionViolationFactoryProvider = CollectionViolationFactorySet()
    }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationFactoryProvider.containsAll(element: E): ViolationFactory<C> =
    containsAll(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationFactoryProvider.containsAll(vararg elements: E): ViolationFactory<C> =
    containsAll(
        elements = elements.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationFactoryProvider.containsNone(element: E): ViolationFactory<C> =
    containsNone(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationFactoryProvider.containsNone(vararg elements: E): ViolationFactory<C> =
    containsNone(
        elements = elements.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationFactoryProvider.containsOnly(element: E): ViolationFactory<C> =
    containsOnly(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationFactoryProvider.containsOnly(vararg elements: E): ViolationFactory<C> =
    containsOnly(
        elements = elements.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionViolationFactoryProvider.sizeBetween(
    minSize: Int,
    maxSize: Int,
): ViolationFactory<C> =
    sizeBetween(
        sizeRange = minSize..maxSize,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionViolationFactoryProvider.sizeNotBetween(
    minSize: Int,
    maxSize: Int,
): ViolationFactory<C> =
    sizeNotBetween(
        sizeRange = minSize..maxSize,
    )
