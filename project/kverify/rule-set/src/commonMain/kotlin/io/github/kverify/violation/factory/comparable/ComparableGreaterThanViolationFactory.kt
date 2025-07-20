package io.github.kverify.violation.factory.comparable

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.comparable.ComparableGreaterThanViolation

public open class ComparableGreaterThanViolationFactory<T : Comparable<T>>(
    public val max: T,
) : ViolationFactory<T> {
    override fun createViolation(value: T): Violation =
        ComparableGreaterThanViolation(
            value = value,
            max = max,
        )
}

public open class NamedComparableGreaterThanViolationFactory<T : Comparable<T>>(
    public val max: T,
) : NamedViolationFactory<T> {
    override fun createViolation(value: NamedValue<T>): Violation =
        ComparableGreaterThanViolation(
            value = value.value,
            max = max,
            name = value.name,
        )
}
