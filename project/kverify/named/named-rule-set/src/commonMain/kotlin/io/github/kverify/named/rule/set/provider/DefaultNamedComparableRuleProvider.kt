package io.github.kverify.named.rule.set.provider

import io.github.kverify.check.set.comparable.ComparableBetweenCheck
import io.github.kverify.check.set.comparable.ComparableEqualToCheck
import io.github.kverify.check.set.comparable.ComparableGreaterThanCheck
import io.github.kverify.check.set.comparable.ComparableGreaterThanOrEqualToCheck
import io.github.kverify.check.set.comparable.ComparableLessThanCheck
import io.github.kverify.check.set.comparable.ComparableLessThanOrEqualToCheck
import io.github.kverify.check.set.comparable.ComparableNotBetweenCheck
import io.github.kverify.check.set.comparable.ComparableNotEqualToCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.violation.factory.provider.DefaultNamedComparableViolationFactoryProvider
import io.github.kverify.named.violation.factory.provider.NamedComparableViolationFactoryProvider
import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider
import io.github.kverify.violation.set.provider.ComparableViolationProvider
import io.github.kverify.violation.set.provider.DefaultComparableViolationProvider

@Suppress("TooManyFunctions")
public class DefaultNamedComparableRuleProvider(
    public override val namedComparableViolationFactoryProvider: NamedComparableViolationFactoryProvider =
        NamedComparableViolationFactoryProvider.Default,
) : NamedComparableRuleProvider {
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

    override fun <T : Comparable<T>> namedBetween(
        range: ClosedRange<T>,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = ComparableBetweenCheck(range),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = ComparableEqualToCheck(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedGreaterThanOrEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = ComparableGreaterThanOrEqualToCheck(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedGreaterThan(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = ComparableGreaterThanCheck(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedLessThanOrEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = ComparableLessThanOrEqualToCheck(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedLessThan(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = ComparableLessThanCheck(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedNotBetween(
        range: ClosedRange<T>,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = ComparableNotBetweenCheck(range),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedNotEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = ComparableNotEqualToCheck(other),
            violationFactory = violationFactory,
        )
}
