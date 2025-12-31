package io.github.kverify.util

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.ViolationReason
import io.github.kverify.violation.set.provider.ComparableViolationProvider

class MockComparableViolationProvider : ComparableViolationProvider {
    override fun <T : Comparable<T>> between(
        value: T,
        range: ClosedRange<T>,
        name: String?,
    ): Violation = ViolationReason("between")

    override fun <T : Comparable<T>> equalTo(
        value: T,
        other: T,
        name: String?,
    ): Violation = ViolationReason("equalTo")

    override fun <T : Comparable<T>> greaterThanOrEqualTo(
        value: T,
        other: T,
        name: String?,
    ): Violation = ViolationReason("greaterThanOrEqualTo")

    override fun <T : Comparable<T>> greaterThan(
        value: T,
        other: T,
        name: String?,
    ): Violation = ViolationReason("greaterThan")

    override fun <T : Comparable<T>> lessThanOrEqualTo(
        value: T,
        other: T,
        name: String?,
    ): Violation = ViolationReason("lessThanOrEqualTo")

    override fun <T : Comparable<T>> lessThan(
        value: T,
        other: T,
        name: String?,
    ): Violation = ViolationReason("lessThan")

    override fun <T : Comparable<T>> notBetween(
        value: T,
        range: ClosedRange<T>,
        name: String?,
    ): Violation = ViolationReason("notBetween")

    override fun <T : Comparable<T>> notEqualTo(
        value: T,
        other: T,
        name: String?,
    ): Violation = ViolationReason("notEqualTo")
}
