package io.github.kverify.rule.set.provider

import io.github.kverify.check.set.collection.CollectionContainsAllCheck
import io.github.kverify.check.set.collection.CollectionContainsCheck
import io.github.kverify.check.set.collection.CollectionContainsNoneCheck
import io.github.kverify.check.set.collection.CollectionContainsOnlyCheck
import io.github.kverify.check.set.collection.CollectionDistinctCheck
import io.github.kverify.check.set.collection.CollectionMaxSizeCheck
import io.github.kverify.check.set.collection.CollectionMinSizeCheck
import io.github.kverify.check.set.collection.CollectionNotContainsCheck
import io.github.kverify.check.set.collection.CollectionNotEmptyCheck
import io.github.kverify.check.set.collection.CollectionNotOfSizeCheck
import io.github.kverify.check.set.collection.CollectionOfSizeCheck
import io.github.kverify.check.set.collection.CollectionSizeBetweenCheck
import io.github.kverify.check.set.collection.CollectionSizeNotBetweenCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider
import io.github.kverify.violation.factory.provider.DefaultCollectionViolationFactoryProvider
import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.set.provider.CollectionViolationProvider
import io.github.kverify.violation.set.provider.DefaultCollectionViolationProvider

@Suppress("TooManyFunctions")
public class DefaultCollectionRuleProvider(
    override val collectionViolationFactoryProvider: CollectionViolationFactoryProvider =
        CollectionViolationFactoryProvider.Default,
) : CollectionRuleProvider {
    public constructor(
        collectionViolationProvider: CollectionViolationProvider,
    ) : this(
        collectionViolationFactoryProvider =
            DefaultCollectionViolationFactoryProvider(
                collectionViolationProvider = collectionViolationProvider,
            ),
    )

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
            validationCheck = CollectionContainsAllCheck(elements),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> containsNone(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = CollectionContainsNoneCheck(elements),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> containsOnly(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = CollectionContainsOnlyCheck(elements),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> contains(
        element: E,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = CollectionContainsCheck(element),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> distinct(violationFactory: ViolationFactory<C>): PredicateRule<C> =
        PredicateRule(
            validationCheck = CollectionDistinctCheck,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> maxSize(
        maxSize: Int,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = CollectionMaxSizeCheck(maxSize),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> minSize(
        minSize: Int,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = CollectionMinSizeCheck(minSize),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> notContains(
        element: E,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = CollectionNotContainsCheck(element),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> notEmpty(violationFactory: ViolationFactory<C>): PredicateRule<C> =
        PredicateRule(
            validationCheck = CollectionNotEmptyCheck,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> notOfSize(
        size: Int,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = CollectionNotOfSizeCheck(size),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> ofSize(
        size: Int,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = CollectionOfSizeCheck(size),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> sizeBetween(
        sizeRange: IntRange,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = CollectionSizeBetweenCheck(sizeRange),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> sizeNotBetween(
        sizeRange: IntRange,
        violationFactory: ViolationFactory<C>,
    ): PredicateRule<C> =
        PredicateRule(
            validationCheck = CollectionSizeNotBetweenCheck(sizeRange),
            violationFactory = violationFactory,
        )
}
