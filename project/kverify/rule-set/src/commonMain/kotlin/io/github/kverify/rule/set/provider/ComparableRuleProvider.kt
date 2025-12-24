package io.github.kverify.rule.set.provider

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.set.provider.ComparableViolationProvider

public interface ComparableRuleProvider {
    public val comparableViolationProvider: ComparableViolationProvider
        get() = ComparableViolationProvider.Default

    public fun <T : Comparable<T>> between(
        range: ClosedRange<T>,
        violationFactory: ViolationFactory<T> =
            ViolationFactory { value ->
                comparableViolationProvider.between(
                    value = value,
                    range = range,
                )
            },
    ): Rule<T>

    public fun <T : Comparable<T>> equalTo(
        other: T,
        violationFactory: ViolationFactory<T> =
            ViolationFactory { value ->
                comparableViolationProvider.equalTo(
                    value = value,
                    other = other,
                )
            },
    ): Rule<T>

    public fun <T : Comparable<T>> greaterThanOrEqualTo(
        other: T,
        violationFactory: ViolationFactory<T> =
            ViolationFactory { value ->
                comparableViolationProvider.greaterThanOrEqualTo(
                    value = value,
                    other = other,
                )
            },
    ): Rule<T>

    public fun <T : Comparable<T>> greaterThan(
        other: T,
        violationFactory: ViolationFactory<T> =
            ViolationFactory { value ->
                comparableViolationProvider.greaterThan(
                    value = value,
                    other = other,
                )
            },
    ): Rule<T>

    public fun <T : Comparable<T>> lessThanOrEqualTo(
        other: T,
        violationFactory: ViolationFactory<T> =
            ViolationFactory { value ->
                comparableViolationProvider.lessThanOrEqualTo(
                    value = value,
                    other = other,
                )
            },
    ): Rule<T>

    public fun <T : Comparable<T>> lessThan(
        other: T,
        violationFactory: ViolationFactory<T> =
            ViolationFactory { value ->
                comparableViolationProvider.lessThan(
                    value = value,
                    other = other,
                )
            },
    ): Rule<T>

    public fun <T : Comparable<T>> notBetween(
        range: ClosedRange<T>,
        violationFactory: ViolationFactory<T> =
            ViolationFactory { value ->
                comparableViolationProvider.notBetween(
                    value = value,
                    range = range,
                )
            },
    ): Rule<T>

    public fun <T : Comparable<T>> notEqualTo(
        other: T,
        violationFactory: ViolationFactory<T> =
            ViolationFactory { value ->
                comparableViolationProvider.notEqualTo(
                    value = value,
                    other = other,
                )
            },
    ): Rule<T>
}
