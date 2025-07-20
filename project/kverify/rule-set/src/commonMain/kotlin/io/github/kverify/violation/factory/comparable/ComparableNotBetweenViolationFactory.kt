package io.github.kverify.violation.factory.comparable

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.comparable.ComparableNotBetweenViolation

public open class ComparableNotBetweenViolationFactory<T : Comparable<T>>(
    public val min: T,
    public val max: T,
) : ViolationFactory<T> {
    override fun createViolation(value: T): Violation =
        ComparableNotBetweenViolation(
            value = value,
            min = min,
            max = max,
        )
}

public open class NamedComparableNotBetweenViolationFactory<T : Comparable<T>>(
    public val min: T,
    public val max: T,
) : NamedViolationFactory<T> {
    override fun createViolation(value: NamedValue<T>): Violation =
        ComparableNotBetweenViolation(
            value = value.value,
            min = min,
            max = max,
            name = value.name,
        )
}
