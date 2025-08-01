package io.github.kverify.violation.factory.classbased.set.comparable

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.ComparableViolationProvider

public class ComparableGreaterThanOrEqualToViolationFactory<T : Comparable<T>>(
    public val other: T,
    public val comparableViolationProvider: ComparableViolationProvider = ComparableViolationProvider.Default,
) : ViolationFactory<T> {
    override fun createViolation(value: T): Violation =
        comparableViolationProvider.greaterThanOrEqualTo(
            value = value,
            other = other,
        )
}
