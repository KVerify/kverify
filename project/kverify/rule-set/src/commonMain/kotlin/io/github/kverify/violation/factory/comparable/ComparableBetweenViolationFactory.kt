package io.github.kverify.violation.factory.comparable

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.comparable.ComparableBetweenViolation

public open class ComparableBetweenViolationFactory<T : Comparable<T>>(
    public val min: T,
    public val max: T,
) : ViolationFactory<T> {
    public constructor(
        range: ClosedRange<T>,
    ) : this(
        min = range.start,
        max = range.endInclusive,
    )

    override fun createViolation(value: T): Violation =
        ComparableBetweenViolation(
            value = value,
            min = min,
            max = max,
        )
}

public open class NamedComparableBetweenViolationFactory<T : Comparable<T>>(
    public val min: T,
    public val max: T,
) : ViolationFactory<NamedValue<T>> {
    public constructor(
        range: ClosedRange<T>,
    ) : this(
        min = range.start,
        max = range.endInclusive,
    )

    override fun createViolation(value: NamedValue<T>): Violation =
        ComparableBetweenViolation(
            value = value.value,
            min = min,
            max = max,
            name = value.name,
        )
}
