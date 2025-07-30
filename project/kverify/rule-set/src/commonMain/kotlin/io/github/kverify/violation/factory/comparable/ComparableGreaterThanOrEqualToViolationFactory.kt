package io.github.kverify.violation.factory.comparable

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.comparable.ComparableGreaterThanOrEqualToViolation

public open class ComparableGreaterThanOrEqualToViolationFactory<T : Comparable<T>>(
    public val max: T,
) : ViolationFactory<T> {
    override fun createViolation(value: T): Violation =
        ComparableGreaterThanOrEqualToViolation(
            value = value,
            max = max,
        )
}

public open class NamedComparableGreaterThanOrEqualToViolationFactory<T : Comparable<T>>(
    public val max: T,
) : NamedViolationFactory<T> {
    override fun createViolation(value: NamedValue<T>): Violation =
        ComparableGreaterThanOrEqualToViolation(
            value = value.value,
            max = max,
            name = value.name,
        )
}
