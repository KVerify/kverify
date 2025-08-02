package io.github.kverify.named.violation.factory.classbased.set.comparable

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.ComparableViolationProvider

public class NamedComparableGreaterThanViolationFactory<T : Comparable<T>>(
    public val other: T,
    public val comparableViolationProvider: ComparableViolationProvider = ComparableViolationProvider.Default,
) : NamedViolationFactory<T> {
    override fun createViolation(value: NamedValue<T>): Violation =
        comparableViolationProvider.greaterThan(
            value = value.value,
            other = other,
            name = value.name,
        )
}
