package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.rule.set.collection.NamedCollectionContainsAllRule
import io.github.kverify.named.rule.set.collection.NamedCollectionContainsNoneRule
import io.github.kverify.named.rule.set.collection.NamedCollectionContainsOnlyRule
import io.github.kverify.named.rule.set.collection.NamedCollectionContainsRule
import io.github.kverify.named.rule.set.collection.NamedCollectionDistinctRule
import io.github.kverify.named.rule.set.collection.NamedCollectionMaxSizeRule
import io.github.kverify.named.rule.set.collection.NamedCollectionMinSizeRule
import io.github.kverify.named.rule.set.collection.NamedCollectionNotContainsRule
import io.github.kverify.named.rule.set.collection.NamedCollectionNotEmptyRule
import io.github.kverify.named.rule.set.collection.NamedCollectionNotOfSizeRule
import io.github.kverify.named.rule.set.collection.NamedCollectionOfSizeRule
import io.github.kverify.named.rule.set.collection.NamedCollectionSizeBetweenRule
import io.github.kverify.named.rule.set.collection.NamedCollectionSizeNotBetweenRule
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactorySet
import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.set.provider.CollectionViolationProvider
import io.github.kverify.violation.set.provider.CollectionViolationSet

@Suppress("TooManyFunctions")
public class NamedCollectionRuleSet(
    public val namedCollectionViolationFactoryProvider: NamedCollectionViolationFactoryProvider =
        NamedCollectionViolationFactoryProvider.Default,
) : NamedCollectionRuleProvider,
    NamedCollectionRuleWithFactoryProvider {
    public constructor(
        collectionViolationProvider: CollectionViolationProvider,
    ) : this(
        namedCollectionViolationFactoryProvider =
            NamedCollectionViolationFactorySet(
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

    override fun <E, C : Collection<E>> namedContainsAll(elements: Collection<E>): NamedRule<C> =
        NamedCollectionContainsAllRule(
            elements = elements,
            violationFactory = namedCollectionViolationFactoryProvider.namedContainsAll(elements),
        )

    override fun <E, C : Collection<E>> namedContainsNone(elements: Collection<E>): NamedRule<C> =
        NamedCollectionContainsNoneRule(
            elements = elements,
            violationFactory = namedCollectionViolationFactoryProvider.namedContainsNone(elements),
        )

    override fun <E, C : Collection<E>> namedContainsOnly(elements: Collection<E>): NamedRule<C> =
        NamedCollectionContainsOnlyRule(
            elements = elements,
            violationFactory = namedCollectionViolationFactoryProvider.namedContainsOnly(elements),
        )

    override fun <E, C : Collection<E>> namedContains(element: E): NamedRule<C> =
        NamedCollectionContainsRule(
            element = element,
            violationFactory = namedCollectionViolationFactoryProvider.namedContains(element),
        )

    override fun <C : Collection<*>> namedDistinct(): NamedRule<C> =
        NamedCollectionDistinctRule(
            violationFactory = namedCollectionViolationFactoryProvider.namedDistinct(),
        )

    override fun <C : Collection<*>> namedMaxSize(maxSize: Int): NamedRule<C> =
        NamedCollectionMaxSizeRule(
            maxSize = maxSize,
            violationFactory = namedCollectionViolationFactoryProvider.namedMaxSize(maxSize),
        )

    override fun <C : Collection<*>> namedMinSize(minSize: Int): NamedRule<C> =
        NamedCollectionMinSizeRule(
            minSize = minSize,
            violationFactory = namedCollectionViolationFactoryProvider.namedMinSize(minSize),
        )

    override fun <E, C : Collection<E>> namedNotContains(element: E): NamedRule<C> =
        NamedCollectionNotContainsRule(
            element = element,
            violationFactory = namedCollectionViolationFactoryProvider.namedNotContains(element),
        )

    override fun <C : Collection<*>> namedNotEmpty(): NamedRule<C> =
        NamedCollectionNotEmptyRule(
            violationFactory = namedCollectionViolationFactoryProvider.namedNotEmpty(),
        )

    override fun <C : Collection<*>> namedNotOfSize(size: Int): NamedRule<C> =
        NamedCollectionNotOfSizeRule(
            size = size,
            violationFactory = namedCollectionViolationFactoryProvider.namedNotOfSize(size),
        )

    override fun <C : Collection<*>> namedOfSize(size: Int): NamedRule<C> =
        NamedCollectionOfSizeRule(
            size = size,
            violationFactory = namedCollectionViolationFactoryProvider.namedOfSize(size),
        )

    override fun <C : Collection<*>> namedSizeBetween(sizeRange: IntRange): NamedRule<C> =
        NamedCollectionSizeBetweenRule(
            sizeRange = sizeRange,
            violationFactory = namedCollectionViolationFactoryProvider.namedSizeBetween(sizeRange),
        )

    override fun <C : Collection<*>> namedSizeNotBetween(sizeRange: IntRange): NamedRule<C> =
        NamedCollectionSizeNotBetweenRule(
            sizeRange = sizeRange,
            violationFactory = namedCollectionViolationFactoryProvider.namedSizeNotBetween(sizeRange),
        )

    override fun <E, C : Collection<E>> namedContainsAll(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C> =
        NamedCollectionContainsAllRule(
            elements = elements,
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> namedContainsNone(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C> =
        NamedCollectionContainsNoneRule(
            elements = elements,
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> namedContainsOnly(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C> =
        NamedCollectionContainsOnlyRule(
            elements = elements,
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> namedContains(
        element: E,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C> =
        NamedCollectionContainsRule(
            element = element,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedDistinct(violationFactory: NamedViolationFactory<C>): NamedRule<C> =
        NamedCollectionDistinctRule(
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedMaxSize(
        maxSize: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C> =
        NamedCollectionMaxSizeRule(
            maxSize = maxSize,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedMinSize(
        minSize: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C> =
        NamedCollectionMinSizeRule(
            minSize = minSize,
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> namedNotContains(
        element: E,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C> =
        NamedCollectionNotContainsRule(
            element = element,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedNotEmpty(violationFactory: NamedViolationFactory<C>): NamedRule<C> =
        NamedCollectionNotEmptyRule(
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedNotOfSize(
        size: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C> =
        NamedCollectionNotOfSizeRule(
            size = size,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedOfSize(
        size: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C> =
        NamedCollectionOfSizeRule(
            size = size,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedSizeBetween(
        sizeRange: IntRange,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C> =
        NamedCollectionSizeBetweenRule(
            sizeRange = sizeRange,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedSizeNotBetween(
        sizeRange: IntRange,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C> =
        NamedCollectionSizeNotBetweenRule(
            sizeRange = sizeRange,
            violationFactory = violationFactory,
        )
}
