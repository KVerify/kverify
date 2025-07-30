package io.github.kverify.violation.factory.comparable

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.comparable.ComparableLessThanViolation

public open class ComparableLessThanViolationFactory<T : Comparable<T>>(
    public val min: T,
) : ViolationFactory<T> {
    override fun createViolation(value: T): Violation =
        ComparableLessThanViolation(
            value = value,
            min = min,
        )
}

public open class NamedComparableLessThanViolationFactory<T : Comparable<T>>(
    public val min: T,
) : NamedViolationFactory<T> {
    override fun createViolation(value: NamedValue<T>): Violation =
        ComparableLessThanViolation(
            value = value.value,
            min = min,
            name = value.name,
        )
}
