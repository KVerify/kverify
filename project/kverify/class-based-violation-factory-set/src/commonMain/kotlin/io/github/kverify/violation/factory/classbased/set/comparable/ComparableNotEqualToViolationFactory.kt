package io.github.kverify.violation.factory.classbased.set.comparable

import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.ComparableViolationProvider

public class ComparableNotEqualToViolationFactory<T : Comparable<T>>(
    public val other: T,
    public val comparableViolationProvider: ComparableViolationProvider = ComparableViolationProvider.Default,
) : ViolationFactory<T> {
    override fun createViolation(value: T): Violation =
        comparableViolationProvider.notEqualTo(
            value = value,
            other = other,
        )
}
