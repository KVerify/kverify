package io.github.kverify.named.violation.factory.classbased.set.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.CollectionViolationProvider

public class NamedCollectionContainsNoneViolationFactory<E, C : Collection<E>>(
    public val elements: Collection<E>,
    public val collectionViolationProvider: CollectionViolationProvider = CollectionViolationProvider.Default,
) : NamedViolationFactory<C> {
    override fun createViolation(value: NamedValue<C>): Violation =
        collectionViolationProvider.containsNone(
            value = value.value,
            elements = elements,
            name = value.name,
        )
}
