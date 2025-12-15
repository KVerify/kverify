package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.rule.set.comparable.NamedComparableBetweenRule
import io.github.kverify.named.rule.set.comparable.NamedComparableEqualToRule
import io.github.kverify.named.rule.set.comparable.NamedComparableGreaterThanOrEqualToRule
import io.github.kverify.named.rule.set.comparable.NamedComparableGreaterThanRule
import io.github.kverify.named.rule.set.comparable.NamedComparableLessThanOrEqualToRule
import io.github.kverify.named.rule.set.comparable.NamedComparableLessThanRule
import io.github.kverify.named.rule.set.comparable.NamedComparableNotBetweenRule
import io.github.kverify.named.rule.set.comparable.NamedComparableNotEqualToRule
import io.github.kverify.named.violation.factory.provider.DefaultNamedComparableViolationFactoryProvider
import io.github.kverify.named.violation.factory.provider.NamedComparableViolationFactoryProvider
import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider
import io.github.kverify.violation.set.provider.ComparableViolationProvider
import io.github.kverify.violation.set.provider.DefaultComparableViolationProvider

@Suppress("TooManyFunctions")
public class DefaultNamedComparableRuleProvider(
    public val namedComparableViolationFactoryProvider: NamedComparableViolationFactoryProvider =
        NamedComparableViolationFactoryProvider.Default,
) : NamedComparableRuleProvider,
    NamedComparableRuleWithFactoryProvider {
    public constructor(
        comparableViolationProvider: ComparableViolationProvider,
    ) : this(
        namedComparableViolationFactoryProvider =
            DefaultNamedComparableViolationFactoryProvider(
                comparableViolationProvider = comparableViolationProvider,
            ),
    )

    public constructor(
        comparableViolationLocalizationProvider: ComparableViolationLocalizationProvider,
    ) : this(
        comparableViolationProvider =
            DefaultComparableViolationProvider(
                comparableViolationLocalizationProvider = comparableViolationLocalizationProvider,
            ),
    )

    override fun <T : Comparable<T>> namedBetween(range: ClosedRange<T>): NamedRule<T> =
        NamedComparableBetweenRule(
            range = range,
            violationFactory = namedComparableViolationFactoryProvider.namedBetween(range),
        )

    override fun <T : Comparable<T>> namedEqualTo(other: T): NamedRule<T> =
        NamedComparableEqualToRule(
            other = other,
            violationFactory = namedComparableViolationFactoryProvider.namedEqualTo(other),
        )

    override fun <T : Comparable<T>> namedGreaterThanOrEqualTo(other: T): NamedRule<T> =
        NamedComparableGreaterThanOrEqualToRule(
            other = other,
            violationFactory = namedComparableViolationFactoryProvider.namedGreaterThanOrEqualTo(other),
        )

    override fun <T : Comparable<T>> namedGreaterThan(other: T): NamedRule<T> =
        NamedComparableGreaterThanRule(
            other = other,
            violationFactory = namedComparableViolationFactoryProvider.namedGreaterThan(other),
        )

    override fun <T : Comparable<T>> namedLessThanOrEqualTo(other: T): NamedRule<T> =
        NamedComparableLessThanOrEqualToRule(
            other = other,
            violationFactory = namedComparableViolationFactoryProvider.namedLessThanOrEqualTo(other),
        )

    override fun <T : Comparable<T>> namedLessThan(other: T): NamedRule<T> =
        NamedComparableLessThanRule(
            other = other,
            violationFactory = namedComparableViolationFactoryProvider.namedLessThan(other),
        )

    override fun <T : Comparable<T>> namedNotBetween(range: ClosedRange<T>): NamedRule<T> =
        NamedComparableNotBetweenRule(
            range = range,
            violationFactory = namedComparableViolationFactoryProvider.namedNotBetween(range),
        )

    override fun <T : Comparable<T>> namedNotEqualTo(other: T): NamedRule<T> =
        NamedComparableNotEqualToRule(
            other = other,
            violationFactory = namedComparableViolationFactoryProvider.namedNotEqualTo(other),
        )

    override fun <T : Comparable<T>> namedBetween(
        range: ClosedRange<T>,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T> =
        NamedComparableBetweenRule(
            range = range,
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T> =
        NamedComparableEqualToRule(
            other = other,
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedGreaterThanOrEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T> =
        NamedComparableGreaterThanOrEqualToRule(
            other = other,
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedGreaterThan(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T> =
        NamedComparableGreaterThanRule(
            other = other,
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedLessThanOrEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T> =
        NamedComparableLessThanOrEqualToRule(
            other = other,
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedLessThan(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T> =
        NamedComparableLessThanRule(
            other = other,
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedNotBetween(
        range: ClosedRange<T>,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T> =
        NamedComparableNotBetweenRule(
            range = range,
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedNotEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedRule<T> =
        NamedComparableNotEqualToRule(
            other = other,
            violationFactory = violationFactory,
        )
}
