package io.github.kverify.named.violation.factory.classbased.set.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.CollectionViolationProvider

public class NamedCollectionMaxSizeViolationFactory<C : Collection<*>>(
    public val maxSize: Int,
    public val collectionViolationProvider: CollectionViolationProvider = CollectionViolationProvider.Default,
) : NamedViolationFactory<C> {
    override fun createViolation(value: NamedValue<C>): Violation =
        collectionViolationProvider.maxSize(
            value = value.value,
            maxSize = maxSize,
            name = value.name,
        )
}
