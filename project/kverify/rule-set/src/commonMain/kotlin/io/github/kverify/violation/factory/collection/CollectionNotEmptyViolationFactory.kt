package io.github.kverify.violation.factory.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.collection.CollectionNotEmptyViolation

public open class CollectionIsNotEmptyViolationFactory<C : Collection<*>> : ViolationFactory<C> {
    override fun createViolation(value: C): Violation =
        CollectionNotEmptyViolation(
            value = value,
        )
}

public open class NamedCollectionIsNotEmptyViolationFactory<C : Collection<*>> : NamedViolationFactory<C> {
    override fun createViolation(value: NamedValue<C>): Violation =
        CollectionNotEmptyViolation(
            value = value.value,
            name = value.name,
        )
}
