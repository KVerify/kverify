package io.github.kverify.named.violation.factory.classbased.set.comparable

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.ComparableViolationProvider

public class NamedComparableBetweenViolationFactory<T : Comparable<T>>(
    public val range: ClosedRange<T>,
    public val comparableViolationProvider: ComparableViolationProvider = ComparableViolationProvider.Default,
) : NamedViolationFactory<T> {
    override fun createViolation(value: NamedValue<T>): Violation =
        comparableViolationProvider.between(
            value = value.value,
            range = range,
            name = value.name,
        )
}
