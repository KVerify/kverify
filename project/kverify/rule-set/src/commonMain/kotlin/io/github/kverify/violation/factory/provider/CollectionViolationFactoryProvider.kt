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

public fun <E, C : Collection<E>> CollectionViolationFactoryProvider.containsAll(vararg elements: E): ViolationFactory<C> =
    this.containsAll(elements.asList())

public fun <E, C : Collection<E>> CollectionViolationFactoryProvider.containsNone(vararg elements: E): ViolationFactory<C> =
    this.containsNone(elements.asList())

public fun <E, C : Collection<E>> CollectionViolationFactoryProvider.containsOnly(vararg elements: E): ViolationFactory<C> =
    this.containsOnly(elements.asList())
