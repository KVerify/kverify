package io.github.kverify.util

import io.github.kverify.check.set.provider.ComparableCheckProvider
import io.github.kverify.core.rule.predicate.check.ValidationCheck

class MockComparableCheckProvider : ComparableCheckProvider {
    override fun <T : Comparable<T>> between(range: ClosedRange<T>): ValidationCheck<T> = MockCheck("between")

    override fun <T : Comparable<T>> equalTo(other: T): ValidationCheck<T> = MockCheck("equalTo")

    override fun <T : Comparable<T>> greaterThanOrEqualTo(other: T): ValidationCheck<T> = MockCheck("greaterThanOrEqualTo")

    override fun <T : Comparable<T>> greaterThan(other: T): ValidationCheck<T> = MockCheck("greaterThan")

    override fun <T : Comparable<T>> lessThanOrEqualTo(other: T): ValidationCheck<T> = MockCheck("lessThanOrEqualTo")

    override fun <T : Comparable<T>> lessThan(other: T): ValidationCheck<T> = MockCheck("lessThan")

    override fun <T : Comparable<T>> notBetween(range: ClosedRange<T>): ValidationCheck<T> = MockCheck("notBetween")

    override fun <T : Comparable<T>> notEqualTo(other: T): ValidationCheck<T> = MockCheck("notEqualTo")
}
