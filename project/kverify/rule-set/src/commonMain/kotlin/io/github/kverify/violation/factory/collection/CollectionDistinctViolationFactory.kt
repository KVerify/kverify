package io.github.kverify.violation.factory.collection

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.collection.CollectionDistinctViolation

public open class CollectionDistinctViolationFactory<C : Collection<*>> : ViolationFactory<C> {
    override fun createViolation(value: C): Violation =
        CollectionDistinctViolation(
            value = value,
        )
}

public open class NamedCollectionDistinctViolationFactory<C : Collection<*>> : NamedViolationFactory<C> {
    override fun createViolation(value: NamedValue<C>): Violation =
        CollectionDistinctViolation(
            value = value.value,
            name = value.name,
        )
}
