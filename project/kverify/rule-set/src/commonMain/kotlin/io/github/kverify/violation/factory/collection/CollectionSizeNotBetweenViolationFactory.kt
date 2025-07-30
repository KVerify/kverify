package io.github.kverify.violation.factory.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.factory.collection.NamedCollectionSizeBetweenViolationFactory
import io.github.kverify.violation.set.collection.CollectionSizeNotBetweenViolation

public open class CollectionSizeNotBetweenViolationFactory<C : Collection<*>>(
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
        CollectionSizeNotBetweenViolation(
            value = value,
            minSize = minSize,
            maxSize = maxSize,
        )
}

public open class NamedCollectionSizeNotBetweenViolationFactory<C : Collection<*>>(
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
        CollectionSizeNotBetweenViolation(
            value = value.value,
            minSize = minSize,
            maxSize = maxSize,
            name = value.name,
        )
}
