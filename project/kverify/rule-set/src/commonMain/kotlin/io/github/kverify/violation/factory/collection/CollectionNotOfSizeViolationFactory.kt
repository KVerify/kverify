package io.github.kverify.violation.factory.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.collection.CollectionNotOfSizeViolation

public open class CollectionNotOfSizeViolationFactory<C : Collection<*>>(
    public val size: Int,
) : ViolationFactory<C> {
    override fun createViolation(value: C): Violation =
        CollectionNotOfSizeViolation(
            value = value,
            size = size,
        )
}

public open class NamedCollectionNotOfSizeViolationFactory<C : Collection<*>>(
    public val size: Int,
) : NamedViolationFactory<C> {
    override fun createViolation(value: NamedValue<C>): Violation =
        CollectionNotOfSizeViolation(
            value = value.value,
            size = size,
            name = value.name,
        )
}
