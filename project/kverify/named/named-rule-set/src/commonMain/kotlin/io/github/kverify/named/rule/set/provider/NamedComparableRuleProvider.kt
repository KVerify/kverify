package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.violation.set.provider.ComparableViolationProvider

public interface NamedComparableRuleProvider {
    public val comparableViolationProvider: ComparableViolationProvider
        get() = ComparableViolationProvider.Default

    public fun <T : Comparable<T>> namedBetween(
        range: ClosedRange<T>,
        violationFactory: NamedViolationFactory<T> =
            NamedViolationFactory { (name, value) ->
                comparableViolationProvider.between(
                    value = value,
                    range = range,
                    name = name,
                )
            },
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T> =
            NamedViolationFactory { (name, value) ->
                comparableViolationProvider.equalTo(
                    value = value,
                    other = other,
                    name = name,
                )
            },
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedGreaterThanOrEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T> =
            NamedViolationFactory { (name, value) ->
                comparableViolationProvider.greaterThanOrEqualTo(
                    value = value,
                    other = other,
                    name = name,
                )
            },
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedGreaterThan(
        other: T,
        violationFactory: NamedViolationFactory<T> =
            NamedViolationFactory { (name, value) ->
                comparableViolationProvider.greaterThan(
                    value = value,
                    other = other,
                    name = name,
                )
            },
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedLessThanOrEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T> =
            NamedViolationFactory { (name, value) ->
                comparableViolationProvider.lessThanOrEqualTo(
                    value = value,
                    other = other,
                    name = name,
                )
            },
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedLessThan(
        other: T,
        violationFactory: NamedViolationFactory<T> =
            NamedViolationFactory { (name, value) ->
                comparableViolationProvider.lessThan(
                    value = value,
                    other = other,
                    name = name,
                )
            },
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedNotBetween(
        range: ClosedRange<T>,
        violationFactory: NamedViolationFactory<T> =
            NamedViolationFactory { (name, value) ->
                comparableViolationProvider.notBetween(
                    value = value,
                    range = range,
                    name = name,
                )
            },
    ): NamedRule<T>

    public fun <T : Comparable<T>> namedNotEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T> =
            NamedViolationFactory { (name, value) ->
                comparableViolationProvider.notEqualTo(
                    value = value,
                    other = other,
                    name = name,
                )
            },
    ): NamedRule<T>
}
