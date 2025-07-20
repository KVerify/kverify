package io.github.kverify.violation.factory.collection

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.collection.CollectionOfSizeViolation

public class CollectionOfSizeViolationFactory<C : Collection<*>>(
    public val size: Int,
) : ViolationFactory<C> {
    override fun createViolation(value: C): Violation =
        CollectionOfSizeViolation(
            value = value,
            size = size,
        )
}

public class NamedCollectionOfSizeViolationFactory<C : Collection<*>>(
    public val size: Int,
) : NamedViolationFactory<C> {
    override fun createViolation(value: NamedValue<C>): Violation =
        CollectionOfSizeViolation(
            value = value.value,
            size = size,
            name = value.name,
        )
}
