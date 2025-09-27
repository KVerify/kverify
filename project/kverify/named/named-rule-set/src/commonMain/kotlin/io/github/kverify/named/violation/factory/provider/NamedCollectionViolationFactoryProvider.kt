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
