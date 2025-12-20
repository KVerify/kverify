package io.github.kverify.rule.set.provider

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.provider.ComparableViolationFactoryProvider

public interface ComparableRuleProvider {
    public val comparableViolationFactoryProvider: ComparableViolationFactoryProvider
        get() = ComparableViolationFactoryProvider.Default

    public fun <T : Comparable<T>> between(
        range: ClosedRange<T>,
        violationFactory: ViolationFactory<T> = comparableViolationFactoryProvider.between(range),
    ): Rule<T>

    public fun <T : Comparable<T>> equalTo(
        other: T,
        violationFactory: ViolationFactory<T> = comparableViolationFactoryProvider.equalTo(other),
    ): Rule<T>

    public fun <T : Comparable<T>> greaterThanOrEqualTo(
        other: T,
        violationFactory: ViolationFactory<T> = comparableViolationFactoryProvider.greaterThanOrEqualTo(other),
    ): Rule<T>

    public fun <T : Comparable<T>> greaterThan(
        other: T,
        violationFactory: ViolationFactory<T> = comparableViolationFactoryProvider.greaterThan(other),
    ): Rule<T>

    public fun <T : Comparable<T>> lessThanOrEqualTo(
        other: T,
        violationFactory: ViolationFactory<T> = comparableViolationFactoryProvider.lessThanOrEqualTo(other),
    ): Rule<T>

    public fun <T : Comparable<T>> lessThan(
        other: T,
        violationFactory: ViolationFactory<T> = comparableViolationFactoryProvider.lessThan(other),
    ): Rule<T>

    public fun <T : Comparable<T>> notBetween(
        range: ClosedRange<T>,
        violationFactory: ViolationFactory<T> = comparableViolationFactoryProvider.notBetween(range),
    ): Rule<T>

    public fun <T : Comparable<T>> notEqualTo(
        other: T,
        violationFactory: ViolationFactory<T> = comparableViolationFactoryProvider.notEqualTo(other),
    ): Rule<T>
}
