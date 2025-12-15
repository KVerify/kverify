package io.github.kverify.rule.set.provider

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.rule.set.collection.CollectionContainsAllRule
import io.github.kverify.rule.set.collection.CollectionContainsNoneRule
import io.github.kverify.rule.set.collection.CollectionContainsOnlyRule
import io.github.kverify.rule.set.collection.CollectionContainsRule
import io.github.kverify.rule.set.collection.CollectionDistinctRule
import io.github.kverify.rule.set.collection.CollectionMaxSizeRule
import io.github.kverify.rule.set.collection.CollectionMinSizeRule
import io.github.kverify.rule.set.collection.CollectionNotContainsRule
import io.github.kverify.rule.set.collection.CollectionNotEmptyRule
import io.github.kverify.rule.set.collection.CollectionNotOfSizeRule
import io.github.kverify.rule.set.collection.CollectionOfSizeRule
import io.github.kverify.rule.set.collection.CollectionSizeBetweenRule
import io.github.kverify.rule.set.collection.CollectionSizeNotBetweenRule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider
import io.github.kverify.violation.factory.provider.CollectionViolationFactorySet
import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.set.provider.CollectionViolationProvider
import io.github.kverify.violation.set.provider.CollectionViolationSet

@Suppress("TooManyFunctions")
public open class CollectionRuleSet(
    public val collectionViolationFactoryProvider: CollectionViolationFactoryProvider =
        CollectionViolationFactoryProvider.Default,
) : CollectionRuleProvider,
    CollectionRuleWithFactoryProvider {
    public constructor(
        collectionViolationProvider: CollectionViolationProvider,
    ) : this(
        collectionViolationFactoryProvider =
            CollectionViolationFactorySet(
                collectionViolationProvider = collectionViolationProvider,
            ),
    )

    public constructor(
        collectionViolationLocalizationProvider: CollectionViolationLocalizationProvider,
    ) : this(
        collectionViolationProvider =
            CollectionViolationSet(
                collectionViolationLocalizationProvider = collectionViolationLocalizationProvider,
            ),
    )

    override fun <E, C : Collection<E>> containsAll(elements: Collection<E>): Rule<C> =
        CollectionContainsAllRule(
            elements = elements,
            violationFactory = collectionViolationFactoryProvider.containsAll(elements),
        )

    override fun <E, C : Collection<E>> containsNone(elements: Collection<E>): Rule<C> =
        CollectionContainsNoneRule(
            elements = elements,
            violationFactory = collectionViolationFactoryProvider.containsNone(elements),
        )

    override fun <E, C : Collection<E>> containsOnly(elements: Collection<E>): Rule<C> =
        CollectionContainsOnlyRule(
            elements = elements,
            violationFactory = collectionViolationFactoryProvider.containsOnly(elements),
        )

    override fun <E, C : Collection<E>> contains(element: E): Rule<C> =
        CollectionContainsRule(
            element = element,
            violationFactory = collectionViolationFactoryProvider.contains(element),
        )

    override fun <C : Collection<*>> distinct(): Rule<C> =
        CollectionDistinctRule(
            violationFactory = collectionViolationFactoryProvider.distinct(),
        )

    override fun <C : Collection<*>> maxSize(maxSize: Int): Rule<C> =
        CollectionMaxSizeRule(
            maxSize = maxSize,
            violationFactory = collectionViolationFactoryProvider.maxSize(maxSize),
        )

    override fun <C : Collection<*>> minSize(minSize: Int): Rule<C> =
        CollectionMinSizeRule(
            minSize = minSize,
            violationFactory = collectionViolationFactoryProvider.minSize(minSize),
        )

    override fun <E, C : Collection<E>> notContains(element: E): Rule<C> =
        CollectionNotContainsRule(
            element = element,
            violationFactory = collectionViolationFactoryProvider.notContains(element),
        )

    override fun <C : Collection<*>> notEmpty(): Rule<C> =
        CollectionNotEmptyRule(
            violationFactory = collectionViolationFactoryProvider.notEmpty(),
        )

    override fun <C : Collection<*>> notOfSize(size: Int): Rule<C> =
        CollectionNotOfSizeRule(
            size = size,
            violationFactory = collectionViolationFactoryProvider.notOfSize(size),
        )

    override fun <C : Collection<*>> ofSize(size: Int): Rule<C> =
        CollectionOfSizeRule(
            size = size,
            violationFactory = collectionViolationFactoryProvider.ofSize(size),
        )

    override fun <C : Collection<*>> sizeBetween(sizeRange: IntRange): Rule<C> =
        CollectionSizeBetweenRule(
            sizeRange = sizeRange,
            violationFactory = collectionViolationFactoryProvider.sizeBetween(sizeRange),
        )

    override fun <C : Collection<*>> sizeNotBetween(sizeRange: IntRange): Rule<C> =
        CollectionSizeNotBetweenRule(
            sizeRange = sizeRange,
            violationFactory = collectionViolationFactoryProvider.sizeNotBetween(sizeRange),
        )

    override fun <E, C : Collection<E>> containsAll(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C>,
    ): Rule<C> =
        CollectionContainsAllRule(
            elements = elements,
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> containsNone(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C>,
    ): Rule<C> =
        CollectionContainsNoneRule(
            elements = elements,
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> containsOnly(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C>,
    ): Rule<C> =
        CollectionContainsOnlyRule(
            elements = elements,
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> contains(
        element: E,
        violationFactory: ViolationFactory<C>,
    ): Rule<C> =
        CollectionContainsRule(
            element = element,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> distinct(violationFactory: ViolationFactory<C>): Rule<C> =
        CollectionDistinctRule(
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> maxSize(
        maxSize: Int,
        violationFactory: ViolationFactory<C>,
    ): Rule<C> =
        CollectionMaxSizeRule(
            maxSize = maxSize,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> minSize(
        minSize: Int,
        violationFactory: ViolationFactory<C>,
    ): Rule<C> =
        CollectionMinSizeRule(
            minSize = minSize,
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> notContains(
        element: E,
        violationFactory: ViolationFactory<C>,
    ): Rule<C> =
        CollectionNotContainsRule(
            element = element,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> notEmpty(violationFactory: ViolationFactory<C>): Rule<C> =
        CollectionNotEmptyRule(
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> notOfSize(
        size: Int,
        violationFactory: ViolationFactory<C>,
    ): Rule<C> =
        CollectionNotOfSizeRule(
            size = size,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> ofSize(
        size: Int,
        violationFactory: ViolationFactory<C>,
    ): Rule<C> =
        CollectionOfSizeRule(
            size = size,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> sizeBetween(
        sizeRange: IntRange,
        violationFactory: ViolationFactory<C>,
    ): Rule<C> =
        CollectionSizeBetweenRule(
            sizeRange = sizeRange,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> sizeNotBetween(
        sizeRange: IntRange,
        violationFactory: ViolationFactory<C>,
    ): Rule<C> =
        CollectionSizeNotBetweenRule(
            sizeRange = sizeRange,
            violationFactory = violationFactory,
        )
}
