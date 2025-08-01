package io.github.kverify.violation.factory.classbased.set.collection

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.CollectionViolationProvider

public class CollectionContainsAllViolationFactory<E, C : Collection<E>>(
    public val elements: Collection<E>,
    public val collectionViolationProvider: CollectionViolationProvider = CollectionViolationProvider.Default,
) : ViolationFactory<C> {
    override fun createViolation(value: C): Violation =
        collectionViolationProvider.containsAll(
            value = value,
            elements = elements,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsAllViolationFactory(
    vararg elements: E,
    collectionViolationProvider: CollectionViolationProvider,
): CollectionContainsAllViolationFactory<E, C> =
    CollectionContainsAllViolationFactory(
        elements = elements.toSet(),
        collectionViolationProvider = collectionViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsAllViolationFactory(
    vararg elements: E,
): CollectionContainsAllViolationFactory<E, C> =
    CollectionContainsAllViolationFactory(
        elements = elements.toSet(),
    )
