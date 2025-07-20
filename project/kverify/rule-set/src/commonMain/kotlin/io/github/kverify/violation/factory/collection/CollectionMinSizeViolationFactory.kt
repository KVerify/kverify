package io.github.kverify.violation.factory.collection

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.collection.CollectionMinSizeViolation

public open class CollectionMinSizeViolationFactory<C : Collection<*>>(
    public val minSize: Int,
) : ViolationFactory<C> {
    override fun createViolation(value: C): Violation =
        CollectionMinSizeViolation(
            value = value,
            minSize = minSize,
        )
}

public open class NamedCollectionMinSizeViolationFactory<C : Collection<*>>(
    public val minSize: Int,
) : NamedViolationFactory<C> {
    override fun createViolation(value: NamedValue<C>): Violation =
        CollectionMinSizeViolation(
            value = value.value,
            minSize = minSize,
            name = value.name,
        )
}
