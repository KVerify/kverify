package io.github.kverify.violation.factory.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.collection.CollectionContainsAllViolation

public open class CollectionContainsAllViolationFactory<E, C : Collection<E>>(
    public val elements: Collection<E>,
) : ViolationFactory<C> {
    public constructor(
        vararg elements: E,
    ) : this(
        elements = elements.asList(),
    )

    override fun createViolation(value: C): Violation =
        CollectionContainsAllViolation(
            value = value,
            elements = elements,
        )
}

public open class NamedCollectionContainsAllViolationFactory<E, C : Collection<E>>(
    public val elements: Collection<E>,
) : NamedViolationFactory<C> {
    public constructor(
        vararg elements: E,
    ) : this(
        elements = elements.asList(),
    )

    override fun createViolation(value: NamedValue<C>): Violation =
        CollectionContainsAllViolation(
            value = value.value,
            elements = elements,
            name = value.name,
        )
}
