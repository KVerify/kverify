package io.github.kverify.violation.factory.provider

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider
import io.github.kverify.violation.set.provider.ComparableViolationProvider
import io.github.kverify.violation.set.provider.ComparableViolations

public interface ComparableViolationFactoryProvider {
    public fun <T : Comparable<T>> between(range: ClosedRange<T>): ViolationFactory<T>

    public fun <T : Comparable<T>> equalTo(other: T): ViolationFactory<T>

    public fun <T : Comparable<T>> greaterThanOrEqualTo(other: T): ViolationFactory<T>

    public fun <T : Comparable<T>> greaterThan(other: T): ViolationFactory<T>

    public fun <T : Comparable<T>> lessThanOrEqualTo(other: T): ViolationFactory<T>

    public fun <T : Comparable<T>> lessThan(other: T): ViolationFactory<T>

    public fun <T : Comparable<T>> notBetween(range: ClosedRange<T>): ViolationFactory<T>

    public fun <T : Comparable<T>> notEqualTo(other: T): ViolationFactory<T>

    public companion object {
        public val Default: ComparableViolationFactoryProvider = ComparableViolationFactories()
    }
}
