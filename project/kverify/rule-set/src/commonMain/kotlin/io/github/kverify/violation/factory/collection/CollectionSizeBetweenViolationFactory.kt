package io.github.kverify.violation.factory.collection

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.collection.CollectionSizeBetweenViolation

public open class CollectionSizeBetweenViolationFactory<C : Collection<*>>(
    public val minSize: Int,
    public val maxSize: Int,
) : ViolationFactory<C> {
    public constructor(
        range: IntRange,
    ) : this(
        minSize = range.first,
        maxSize = range.last,
    )

    override fun createViolation(value: C): Violation =
        CollectionSizeBetweenViolation(
            value = value,
            minSize = minSize,
            maxSize = maxSize,
        )
}

public open class NamedCollectionSizeBetweenViolationFactory<C : Collection<*>>(
    public val minSize: Int,
    public val maxSize: Int,
) : NamedViolationFactory<C> {
    public constructor(
        range: IntRange,
    ) : this(
        minSize = range.first,
        maxSize = range.last,
    )

    override fun createViolation(value: NamedValue<C>): Violation =
        CollectionSizeBetweenViolation(
            value = value.value,
            minSize = minSize,
            maxSize = maxSize,
            name = value.name,
        )
}
