package io.github.kverify.violation.factory.comparable

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.comparable.ComparableLessThanOrEqualToViolation

public open class ComparableLessThanOrEqualToViolationFactory<T : Comparable<T>>(
    public val min: T,
) : ViolationFactory<T> {
    override fun createViolation(value: T): Violation =
        ComparableLessThanOrEqualToViolation(
            value = value,
            min = min,
        )
}

public open class NamedComparableLessThanOrEqualToViolationFactory<T : Comparable<T>>(
    public val min: T,
) : NamedViolationFactory<T> {
    override fun createViolation(value: NamedValue<T>): Violation =
        ComparableLessThanOrEqualToViolation(
            value = value.value,
            min = min,
            name = value.name,
        )
}
