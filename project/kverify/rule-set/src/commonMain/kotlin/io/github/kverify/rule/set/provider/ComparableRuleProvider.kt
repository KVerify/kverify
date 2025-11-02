package io.github.kverify.rule.set.provider

import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.rule.Rule

public interface ComparableRuleProvider {
    public fun <T : Comparable<T>> between(range: ClosedRange<T>): Rule<T>

    public fun <T : Comparable<T>> equalTo(other: T): Rule<T>

    public fun <T : Comparable<T>> greaterThanOrEqualTo(other: T): Rule<T>

    public fun <T : Comparable<T>> greaterThan(other: T): Rule<T>

    public fun <T : Comparable<T>> lessThanOrEqualTo(other: T): Rule<T>

    public fun <T : Comparable<T>> lessThan(other: T): Rule<T>

    public fun <T : Comparable<T>> notBetween(range: ClosedRange<T>): Rule<T>

    public fun <T : Comparable<T>> notEqualTo(other: T): Rule<T>
}

public interface ComparableRuleWithFactoryProvider {
    public fun <T : Comparable<T>> between(
        range: ClosedRange<T>,
        violationFactory: ViolationFactory<T>,
    ): Rule<T>

    public fun <T : Comparable<T>> equalTo(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): Rule<T>

    public fun <T : Comparable<T>> greaterThanOrEqualTo(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): Rule<T>

    public fun <T : Comparable<T>> greaterThan(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): Rule<T>

    public fun <T : Comparable<T>> lessThanOrEqualTo(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): Rule<T>

    public fun <T : Comparable<T>> lessThan(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): Rule<T>

    public fun <T : Comparable<T>> notBetween(
        range: ClosedRange<T>,
        violationFactory: ViolationFactory<T>,
    ): Rule<T>

    public fun <T : Comparable<T>> notEqualTo(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): Rule<T>
}
