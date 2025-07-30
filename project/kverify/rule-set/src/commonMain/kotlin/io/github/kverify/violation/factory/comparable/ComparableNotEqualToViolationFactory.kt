package io.github.kverify.violation.factory.comparable

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.comparable.ComparableNotEqualToViolation

public open class ComparableNotEqualToViolationFactory<T : Comparable<T>>(
    public val other: T,
) : ViolationFactory<T> {
    override fun createViolation(value: T): Violation =
        ComparableNotEqualToViolation(
            value = value,
            other = other,
        )
}

public open class NamedComparableNotEqualToViolationFactory<T : Comparable<T>>(
    public val other: T,
) : NamedViolationFactory<T> {
    override fun createViolation(value: NamedValue<T>): Violation =
        ComparableNotEqualToViolation(
            value = value.value,
            other = other,
            name = value.name,
        )
}
