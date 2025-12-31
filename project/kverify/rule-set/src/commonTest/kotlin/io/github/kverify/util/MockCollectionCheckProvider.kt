package io.github.kverify.util

import io.github.kverify.check.set.provider.CollectionCheckProvider
import io.github.kverify.core.rule.predicate.check.ValidationCheck

class MockCollectionCheckProvider : CollectionCheckProvider {
    override fun <E, C : Collection<E>> contains(element: E): ValidationCheck<C> = MockCheck("contains")

    override fun <E, C : Collection<E>> containsAll(elements: Collection<E>): ValidationCheck<C> = MockCheck("containsAll")

    override fun <E, C : Collection<E>> containsNone(elements: Collection<E>): ValidationCheck<C> = MockCheck("containsNone")

    override fun <E, C : Collection<E>> containsOnly(elements: Collection<E>): ValidationCheck<C> = MockCheck("containsOnly")

    override fun <E, C : Collection<E>> notContains(element: E): ValidationCheck<C> = MockCheck("notContains")

    override fun notEmpty(): ValidationCheck<Collection<*>> = MockCheck("notEmpty")

    override fun distinct(): ValidationCheck<Collection<*>> = MockCheck("distinct")

    override fun ofSize(size: Int): ValidationCheck<Collection<*>> = MockCheck("ofSize")

    override fun notOfSize(size: Int): ValidationCheck<Collection<*>> = MockCheck("notOfSize")

    override fun minSize(minSize: Int): ValidationCheck<Collection<*>> = MockCheck("minSize")

    override fun maxSize(maxSize: Int): ValidationCheck<Collection<*>> = MockCheck("maxSize")

    override fun sizeBetween(sizeRange: IntRange): ValidationCheck<Collection<*>> = MockCheck("sizeBetween")

    override fun sizeNotBetween(sizeRange: IntRange): ValidationCheck<Collection<*>> = MockCheck("sizeNotBetween")
}
