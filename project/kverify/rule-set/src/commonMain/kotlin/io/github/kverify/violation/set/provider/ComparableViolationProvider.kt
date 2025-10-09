package io.github.kverify.violation.set.provider

import io.github.kverify.core.violation.Violation

public interface ComparableViolationProvider {
    public fun <T : Comparable<T>> between(
        value: T,
        range: ClosedRange<T>,
        name: String? = null,
    ): Violation

    public fun <T : Comparable<T>> equalTo(
        value: T,
        other: T,
        name: String? = null,
    ): Violation

    public fun <T : Comparable<T>> greaterThanOrEqualTo(
        value: T,
        other: T,
        name: String? = null,
    ): Violation

    public fun <T : Comparable<T>> greaterThan(
        value: T,
        other: T,
        name: String? = null,
    ): Violation

    public fun <T : Comparable<T>> lessThanOrEqualTo(
        value: T,
        other: T,
        name: String? = null,
    ): Violation

    public fun <T : Comparable<T>> lessThan(
        value: T,
        other: T,
        name: String? = null,
    ): Violation

    public fun <T : Comparable<T>> notBetween(
        value: T,
        range: ClosedRange<T>,
        name: String? = null,
    ): Violation

    public fun <T : Comparable<T>> notEqualTo(
        value: T,
        other: T,
        name: String? = null,
    ): Violation

    public companion object {
        public val Default: ComparableViolationProvider = ComparableViolationSet()
    }
}
