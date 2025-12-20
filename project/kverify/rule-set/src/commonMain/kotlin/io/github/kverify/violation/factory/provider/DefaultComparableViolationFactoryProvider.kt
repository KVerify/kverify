package io.github.kverify.violation.factory.provider

import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider
import io.github.kverify.violation.set.provider.ComparableViolationProvider
import io.github.kverify.violation.set.provider.DefaultComparableViolationProvider

public class DefaultComparableViolationFactoryProvider(
    public val comparableViolationProvider: ComparableViolationProvider = ComparableViolationProvider.Default,
) : ComparableViolationFactoryProvider {
    public constructor(
        comparableViolationLocalizationProvider: ComparableViolationLocalizationProvider,
    ) : this(
        comparableViolationProvider =
            DefaultComparableViolationProvider(
                comparableViolationLocalizationProvider = comparableViolationLocalizationProvider,
            ),
    )

    override fun <T : Comparable<T>> between(range: ClosedRange<T>): ViolationFactory<T> =
        ViolationFactory { value ->
            comparableViolationProvider.between(
                value = value,
                range = range,
            )
        }

    override fun <T : Comparable<T>> equalTo(other: T): ViolationFactory<T> =
        ViolationFactory { value ->
            comparableViolationProvider.equalTo(
                value = value,
                other = other,
            )
        }

    override fun <T : Comparable<T>> greaterThanOrEqualTo(other: T): ViolationFactory<T> =
        ViolationFactory { value ->
            comparableViolationProvider.greaterThanOrEqualTo(
                value = value,
                other = other,
            )
        }

    override fun <T : Comparable<T>> greaterThan(other: T): ViolationFactory<T> =
        ViolationFactory { value ->
            comparableViolationProvider.greaterThan(
                value = value,
                other = other,
            )
        }

    override fun <T : Comparable<T>> lessThanOrEqualTo(other: T): ViolationFactory<T> =
        ViolationFactory { value ->
            comparableViolationProvider.lessThanOrEqualTo(
                value = value,
                other = other,
            )
        }

    override fun <T : Comparable<T>> lessThan(other: T): ViolationFactory<T> =
        ViolationFactory { value ->
            comparableViolationProvider.lessThan(
                value = value,
                other = other,
            )
        }

    override fun <T : Comparable<T>> notBetween(range: ClosedRange<T>): ViolationFactory<T> =
        ViolationFactory { value ->
            comparableViolationProvider.notBetween(
                value = value,
                range = range,
            )
        }

    override fun <T : Comparable<T>> notEqualTo(other: T): ViolationFactory<T> =
        ViolationFactory { value ->
            comparableViolationProvider.notEqualTo(
                value = value,
                other = other,
            )
        }
}
