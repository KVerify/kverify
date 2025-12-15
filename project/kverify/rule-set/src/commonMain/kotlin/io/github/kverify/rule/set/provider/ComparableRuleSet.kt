package io.github.kverify.rule.set.provider

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.rule.set.comparable.ComparableBetweenRule
import io.github.kverify.rule.set.comparable.ComparableEqualToRule
import io.github.kverify.rule.set.comparable.ComparableGreaterThanOrEqualToRule
import io.github.kverify.rule.set.comparable.ComparableGreaterThanRule
import io.github.kverify.rule.set.comparable.ComparableLessThanOrEqualToRule
import io.github.kverify.rule.set.comparable.ComparableLessThanRule
import io.github.kverify.rule.set.comparable.ComparableNotBetweenRule
import io.github.kverify.rule.set.comparable.ComparableNotEqualToRule
import io.github.kverify.violation.factory.provider.ComparableViolationFactoryProvider
import io.github.kverify.violation.factory.provider.ComparableViolationFactorySet
import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider
import io.github.kverify.violation.set.provider.ComparableViolationProvider
import io.github.kverify.violation.set.provider.ComparableViolationSet

@Suppress("TooManyFunctions")
public open class ComparableRuleSet(
    public val comparableViolationFactoryProvider: ComparableViolationFactoryProvider =
        ComparableViolationFactoryProvider.Default,
) : ComparableRuleProvider,
    ComparableRuleWithFactoryProvider {
    public constructor(
        comparableViolationProvider: ComparableViolationProvider,
    ) : this(
        comparableViolationFactoryProvider =
            ComparableViolationFactorySet(
                comparableViolationProvider = comparableViolationProvider,
            ),
    )

    public constructor(
        comparableViolationLocalizationProvider: ComparableViolationLocalizationProvider,
    ) : this(
        comparableViolationProvider =
            ComparableViolationSet(
                comparableViolationLocalizationProvider = comparableViolationLocalizationProvider,
            ),
    )

    override fun <T : Comparable<T>> between(range: ClosedRange<T>): Rule<T> =
        ComparableBetweenRule(
            range = range,
            violationFactory = comparableViolationFactoryProvider.between(range),
        )

    override fun <T : Comparable<T>> equalTo(other: T): Rule<T> =
        ComparableEqualToRule(
            other = other,
            violationFactory = comparableViolationFactoryProvider.equalTo(other),
        )

    override fun <T : Comparable<T>> greaterThanOrEqualTo(other: T): Rule<T> =
        ComparableGreaterThanOrEqualToRule(
            other = other,
            violationFactory = comparableViolationFactoryProvider.greaterThanOrEqualTo(other),
        )

    override fun <T : Comparable<T>> greaterThan(other: T): Rule<T> =
        ComparableGreaterThanRule(
            other = other,
            violationFactory = comparableViolationFactoryProvider.greaterThan(other),
        )

    override fun <T : Comparable<T>> lessThanOrEqualTo(other: T): Rule<T> =
        ComparableLessThanOrEqualToRule(
            other = other,
            violationFactory = comparableViolationFactoryProvider.lessThanOrEqualTo(other),
        )

    override fun <T : Comparable<T>> lessThan(other: T): Rule<T> =
        ComparableLessThanRule(
            other = other,
            violationFactory = comparableViolationFactoryProvider.lessThan(other),
        )

    override fun <T : Comparable<T>> notBetween(range: ClosedRange<T>): Rule<T> =
        ComparableNotBetweenRule(
            range = range,
            violationFactory = comparableViolationFactoryProvider.notBetween(range),
        )

    override fun <T : Comparable<T>> notEqualTo(other: T): Rule<T> =
        ComparableNotEqualToRule(
            other = other,
            violationFactory = comparableViolationFactoryProvider.notEqualTo(other),
        )

    override fun <T : Comparable<T>> between(
        range: ClosedRange<T>,
        violationFactory: ViolationFactory<T>,
    ): Rule<T> =
        ComparableBetweenRule(
            range = range,
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> equalTo(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): Rule<T> =
        ComparableEqualToRule(
            other = other,
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> greaterThanOrEqualTo(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): Rule<T> =
        ComparableGreaterThanOrEqualToRule(
            other = other,
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> greaterThan(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): Rule<T> =
        ComparableGreaterThanRule(
            other = other,
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> lessThanOrEqualTo(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): Rule<T> =
        ComparableLessThanOrEqualToRule(
            other = other,
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> lessThan(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): Rule<T> =
        ComparableLessThanRule(
            other = other,
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> notBetween(
        range: ClosedRange<T>,
        violationFactory: ViolationFactory<T>,
    ): Rule<T> =
        ComparableNotBetweenRule(
            range = range,
            violationFactory = violationFactory,
        )

    override fun <T : Comparable<T>> notEqualTo(
        other: T,
        violationFactory: ViolationFactory<T>,
    ): Rule<T> =
        ComparableNotEqualToRule(
            other = other,
            violationFactory = violationFactory,
        )
}
