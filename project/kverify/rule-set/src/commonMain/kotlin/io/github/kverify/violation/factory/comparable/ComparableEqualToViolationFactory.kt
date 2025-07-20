package io.github.kverify.violation.factory.comparable

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.comparable.ComparableEqualToViolation

public open class ComparableEqualToViolationFactory<T : Comparable<T>>(
    public val other: T,
) : ViolationFactory<T> {
    override fun createViolation(value: T): Violation =
        ComparableEqualToViolation(
            value = value,
            other = other,
        )
}

public open class NamedComparableEqualToViolationFactory<T : Comparable<T>>(
    public val other: T,
) : NamedViolationFactory<T> {
    override fun createViolation(value: NamedValue<T>): Violation =
        ComparableEqualToViolation(
            value = value.value,
            other = other,
            name = value.name,
        )
}
