package io.github.kverify.rule.set.provider

import io.github.kverify.check.set.comparable.ComparableBetweenCheck
import io.github.kverify.check.set.comparable.ComparableEqualToCheck
import io.github.kverify.check.set.comparable.ComparableGreaterThanCheck
import io.github.kverify.check.set.comparable.ComparableGreaterThanOrEqualToCheck
import io.github.kverify.check.set.comparable.ComparableLessThanCheck
import io.github.kverify.check.set.comparable.ComparableLessThanOrEqualToCheck
import io.github.kverify.check.set.comparable.ComparableNotBetweenCheck
import io.github.kverify.check.set.comparable.ComparableNotEqualToCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.provider.ComparableViolationFactoryProvider
import io.github.kverify.violation.factory.provider.DefaultComparableViolationFactoryProvider
import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider
import io.github.kverify.violation.set.provider.ComparableViolationProvider
import io.github.kverify.violation.set.provider.DefaultComparableViolationProvider

public class DefaultComparableRuleProvider(
    override val comparableViolationFactoryProvider: ComparableViolationFactoryProvider =
        ComparableViolationFactoryProvider.Default,
) : ComparableRuleProvider {
    public constructor(
        comparableViolationProvider: ComparableViolationProvider,
    ) : this(
        comparableViolationFactoryProvider =
            DefaultComparableViolationFactoryProvider(
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

    override fun <T : Comparable<T>> between(
        range: ClosedRange<T>,
        violationFactory: ViolationFactory<T>,
    ): PredicateRule<T> =
        PredicateRule(
            validationCheck = ComparableBetweenCheck(range),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> equalTo(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): PredicateRule<T> =
        PredicateRule(
            validationCheck = ComparableEqualToCheck(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> greaterThanOrEqualTo(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): PredicateRule<T> =
        PredicateRule(
            validationCheck = ComparableGreaterThanOrEqualToCheck(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> greaterThan(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): PredicateRule<T> =
        PredicateRule(
            validationCheck = ComparableGreaterThanCheck(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> lessThanOrEqualTo(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): PredicateRule<T> =
        PredicateRule(
            validationCheck = ComparableLessThanOrEqualToCheck(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> lessThan(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): PredicateRule<T> =
        PredicateRule(
            validationCheck = ComparableLessThanCheck(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> notBetween(
        range: ClosedRange<T>,
        violationFactory: ViolationFactory<T>,
    ): PredicateRule<T> =
        PredicateRule(
            validationCheck = ComparableNotBetweenCheck(range),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> notEqualTo(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): PredicateRule<T> =
        PredicateRule(
            validationCheck = ComparableNotEqualToCheck(other),
            violationFactory = violationFactory,
        )
}
