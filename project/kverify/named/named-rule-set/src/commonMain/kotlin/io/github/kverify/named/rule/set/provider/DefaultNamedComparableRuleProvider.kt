package io.github.kverify.named.rule.set.provider

import io.github.kverify.check.set.provider.ComparableCheckProvider
import io.github.kverify.check.set.provider.DefaultComparableCheckProvider
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.violation.factory.provider.DefaultNamedComparableViolationFactoryProvider
import io.github.kverify.named.violation.factory.provider.NamedComparableViolationFactoryProvider
import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider
import io.github.kverify.violation.set.provider.ComparableViolationProvider
import io.github.kverify.violation.set.provider.DefaultComparableViolationProvider

@Suppress("TooManyFunctions")
public class DefaultNamedComparableRuleProvider(
    public val comparableCheckProvider: ComparableCheckProvider = DefaultComparableCheckProvider(),
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
            validationCheck = comparableCheckProvider.between(range),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = comparableCheckProvider.equalTo(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedGreaterThanOrEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = comparableCheckProvider.greaterThanOrEqualTo(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedGreaterThan(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = comparableCheckProvider.greaterThan(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedLessThanOrEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = comparableCheckProvider.lessThanOrEqualTo(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedLessThan(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = comparableCheckProvider.lessThan(other),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedNotBetween(
        range: ClosedRange<T>,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = comparableCheckProvider.notBetween(range),
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> namedNotEqualTo(
        other: T,
        violationFactory: NamedViolationFactory<T>,
    ): NamedPredicateRule<T> =
        NamedPredicateRule(
            validationCheck = comparableCheckProvider.notEqualTo(other),
            violationFactory = violationFactory,
        )
}
