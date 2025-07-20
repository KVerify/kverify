package io.github.kverify.violation.factory.collection

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.collection.CollectionMaxSizeViolation

public open class CollectionMaxSizeViolationFactory<C : Collection<*>>(
    public val maxSize: Int,
) : ViolationFactory<C> {
    override fun createViolation(value: C): Violation =
        CollectionMaxSizeViolation(
            value = value,
            maxSize = maxSize,
        )
}

public open class NamedCollectionMaxSizeViolationFactory<C : Collection<*>>(
    public val maxSize: Int,
) : NamedViolationFactory<C> {
    override fun createViolation(value: NamedValue<C>): Violation =
        CollectionMaxSizeViolation(
            value = value.value,
            maxSize = maxSize,
            name = value.name,
        )
}
