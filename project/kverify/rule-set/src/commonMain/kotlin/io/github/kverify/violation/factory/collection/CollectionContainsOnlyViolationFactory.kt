package io.github.kverify.violation.factory.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.collection.CollectionContainsOnlyViolation

public open class CollectionContainsOnlyViolationFactory<E, C : Collection<E>>(
    public val elements: Collection<E>,
) : ViolationFactory<C> {
    public constructor(
        vararg elements: E,
    ) : this(
        elements = elements.asList(),
    )

    override fun createViolation(value: C): Violation =
        CollectionContainsOnlyViolation(
            value = value,
            elements = elements,
        )
}

public open class NamedCollectionContainsOnlyViolationFactory<E, C : Collection<E>>(
    public val elements: Collection<E>,
) : NamedViolationFactory<C> {
    public constructor(
        vararg elements: E,
    ) : this(
        elements = elements.asList(),
    )

    override fun createViolation(value: NamedValue<C>): Violation =
        CollectionContainsOnlyViolation(
            value = value.value,
            elements = elements,
            name = value.name,
        )
}
