package io.github.kverify.rule.set.provider

import io.github.kverify.check.set.provider.CollectionCheckProvider
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.set.provider.CollectionViolationProvider
import io.github.kverify.violation.set.provider.DefaultCollectionViolationProvider

@Suppress("TooManyFunctions")
public class DefaultCollectionRuleProvider(
    public val collectionCheckProvider: CollectionCheckProvider = CollectionCheckProvider.Default,
    override val collectionViolationProvider: CollectionViolationProvider = CollectionViolationProvider.Default,
) : CollectionRuleProvider {
    public constructor(
        collectionViolationLocalizationProvider: CollectionViolationLocalizationProvider,
    ) : this(
        collectionViolationProvider =
            DefaultCollectionViolationProvider(
                collectionViolationLocalizationProvider = collectionViolationLocalizationProvider,
            ),
    )

    override fun <E, C : Collection<E>> containsAll(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = collectionCheckProvider.containsAll(elements),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> containsNone(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = collectionCheckProvider.containsNone(elements),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> containsOnly(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = collectionCheckProvider.containsOnly(elements),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> contains(
        element: E,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = collectionCheckProvider.contains(element),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> distinct(violationFactory: ViolationFactory<C>): PredicateRule<C> =
        PredicateRule(
            validationCheck = collectionCheckProvider.distinct(),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> maxSize(
        maxSize: Int,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = collectionCheckProvider.maxSize(maxSize),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> minSize(
        minSize: Int,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = collectionCheckProvider.minSize(minSize),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> notContains(
        element: E,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = collectionCheckProvider.notContains(element),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> notEmpty(violationFactory: ViolationFactory<C>): PredicateRule<C> =
        PredicateRule(
            validationCheck = collectionCheckProvider.notEmpty(),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> notOfSize(
        size: Int,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = collectionCheckProvider.notOfSize(size),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> ofSize(
        size: Int,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = collectionCheckProvider.ofSize(size),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> sizeBetween(
        sizeRange: IntRange,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = collectionCheckProvider.sizeBetween(sizeRange),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> sizeNotBetween(
        sizeRange: IntRange,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = collectionCheckProvider.sizeNotBetween(sizeRange),
            violationFactory = violationFactory,
        )
}
