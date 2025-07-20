package io.github.kverify.violation.factory.collection

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.collection.CollectionContainsViolation

public open class CollectionContainsViolationFactory<E, C : Collection<E>>(
    public val element: E,
) : ViolationFactory<C> {
    override fun createViolation(value: C): Violation =
        CollectionContainsViolation(
            value = value,
            element = element,
        )
}

public open class NamedCollectionContainsViolationFactory<E, C : Collection<E>>(
    public val element: E,
) : NamedViolationFactory<C> {
    override fun createViolation(value: NamedValue<C>): Violation =
        CollectionContainsViolation(
            value = value.value,
            element = element,
            name = value.name,
        )
}
