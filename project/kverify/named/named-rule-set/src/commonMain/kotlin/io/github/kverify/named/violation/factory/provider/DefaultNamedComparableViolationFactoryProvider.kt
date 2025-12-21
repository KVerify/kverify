package io.github.kverify.named.violation.factory.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.violation.set.provider.ComparableViolationProvider

public class DefaultNamedComparableViolationFactoryProvider(
    public val comparableViolationProvider: ComparableViolationProvider = ComparableViolationProvider.Default,
) : NamedComparableViolationFactoryProvider {
    override fun <T : Comparable<T>> namedBetween(range: ClosedRange<T>): NamedViolationFactory<T> =
        NamedViolationFactory { (name, value) ->
            comparableViolationProvider.between(
                value = value,
                range = range,
                name = name,
            )
        }

    override fun <T : Comparable<T>> namedEqualTo(other: T): NamedViolationFactory<T> =
        NamedViolationFactory { (name, value) ->
            comparableViolationProvider.equalTo(
                value = value,
                other = other,
                name = name,
            )
        }

    override fun <T : Comparable<T>> namedGreaterThanOrEqualTo(other: T): NamedViolationFactory<T> =
        NamedViolationFactory { (name, value) ->
            comparableViolationProvider.greaterThanOrEqualTo(
                value = value,
                other = other,
                name = name,
            )
        }

    override fun <T : Comparable<T>> namedGreaterThan(other: T): NamedViolationFactory<T> =
        NamedViolationFactory { (name, value) ->
            comparableViolationProvider.greaterThan(
                value = value,
                other = other,
                name = name,
            )
        }

    override fun <T : Comparable<T>> namedLessThanOrEqualTo(other: T): NamedViolationFactory<T> =
        NamedViolationFactory { (name, value) ->
            comparableViolationProvider.lessThanOrEqualTo(
                value = value,
                other = other,
                name = name,
            )
        }

    override fun <T : Comparable<T>> namedLessThan(other: T): NamedViolationFactory<T> =
        NamedViolationFactory { (name, value) ->
            comparableViolationProvider.lessThan(
                value = value,
                other = other,
                name = name,
            )
        }

    override fun <T : Comparable<T>> namedNotBetween(range: ClosedRange<T>): NamedViolationFactory<T> =
        NamedViolationFactory { (name, value) ->
            comparableViolationProvider.notBetween(
                value = value,
                range = range,
                name = name,
            )
        }

    override fun <T : Comparable<T>> namedNotEqualTo(other: T): NamedViolationFactory<T> =
        NamedViolationFactory { (name, value) ->
            comparableViolationProvider.notEqualTo(
                value = value,
                other = other,
                name = name,
            )
        }
}
