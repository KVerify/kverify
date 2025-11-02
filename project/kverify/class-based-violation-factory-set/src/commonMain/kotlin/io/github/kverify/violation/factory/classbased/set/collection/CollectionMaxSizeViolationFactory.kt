package io.github.kverify.violation.factory.classbased.set.collection

import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.CollectionViolationProvider

public class CollectionMaxSizeViolationFactory<C : Collection<*>>(
    public val maxSize: Int,
    public val collectionViolationProvider: CollectionViolationProvider = CollectionViolationProvider.Default,
) : ViolationFactory<C> {
    override fun createViolation(value: C): Violation =
        collectionViolationProvider.maxSize(
            value = value,
            maxSize = maxSize,
        )
}
