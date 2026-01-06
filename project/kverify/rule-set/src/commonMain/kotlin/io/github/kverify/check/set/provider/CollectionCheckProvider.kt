package io.github.kverify.check.set.provider

import io.github.kverify.core.rule.predicate.check.ValidationCheck

public interface CollectionCheckProvider {
    public fun <E, C : Collection<E>> contains(element: E): ValidationCheck<C>

    public fun <E, C : Collection<E>> containsAll(elements: Collection<E>): ValidationCheck<C>

    public fun <E, C : Collection<E>> containsNone(elements: Collection<E>): ValidationCheck<C>

    public fun <E, C : Collection<E>> containsOnly(elements: Collection<E>): ValidationCheck<C>

    public fun <E, C : Collection<E>> notContains(element: E): ValidationCheck<C>

    public fun notEmpty(): ValidationCheck<Collection<*>>

    public fun distinct(): ValidationCheck<Collection<*>>

    public fun ofSize(size: Int): ValidationCheck<Collection<*>>

    public fun notOfSize(size: Int): ValidationCheck<Collection<*>>

    public fun minSize(minSize: Int): ValidationCheck<Collection<*>>

    public fun maxSize(maxSize: Int): ValidationCheck<Collection<*>>

    public fun sizeBetween(sizeRange: IntRange): ValidationCheck<Collection<*>>

    public fun sizeNotBetween(sizeRange: IntRange): ValidationCheck<Collection<*>>

    public companion object {
        public val Default: CollectionCheckProvider = DefaultCollectionCheckProvider()
    }
}
