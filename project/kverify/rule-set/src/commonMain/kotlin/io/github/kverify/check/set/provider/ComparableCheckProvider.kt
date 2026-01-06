package io.github.kverify.check.set.provider

import io.github.kverify.core.rule.predicate.check.ValidationCheck

public interface ComparableCheckProvider {
    public fun <T : Comparable<T>> equalTo(other: T): ValidationCheck<T>

    public fun <T : Comparable<T>> notEqualTo(other: T): ValidationCheck<T>

    public fun <T : Comparable<T>> greaterThan(other: T): ValidationCheck<T>

    public fun <T : Comparable<T>> greaterThanOrEqualTo(other: T): ValidationCheck<T>

    public fun <T : Comparable<T>> lessThan(other: T): ValidationCheck<T>

    public fun <T : Comparable<T>> lessThanOrEqualTo(other: T): ValidationCheck<T>

    public fun <T : Comparable<T>> between(range: ClosedRange<T>): ValidationCheck<T>

    public fun <T : Comparable<T>> notBetween(range: ClosedRange<T>): ValidationCheck<T>

    public companion object {
        public val Default: ComparableCheckProvider = DefaultComparableCheckProvider()
    }
}
