package io.github.kverify.named.rule.set.provider

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
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.violation.factory.provider.DefaultNamedCollectionViolationFactoryProvider
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider
import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.set.provider.CollectionViolationProvider
import io.github.kverify.violation.set.provider.DefaultCollectionViolationProvider

@Suppress("TooManyFunctions")
public class DefaultNamedCollectionRuleProvider(
    public override val namedCollectionViolationFactoryProvider: NamedCollectionViolationFactoryProvider =
        NamedCollectionViolationFactoryProvider.Default,
) : NamedCollectionRuleProvider {
    public constructor(
        collectionViolationProvider: CollectionViolationProvider,
    ) : this(
        namedCollectionViolationFactoryProvider =
            DefaultNamedCollectionViolationFactoryProvider(
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

    override fun <E, C : Collection<E>> namedContainsAll(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = CollectionContainsAllCheck(elements),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> namedContainsNone(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = CollectionContainsNoneCheck(elements),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> namedContainsOnly(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = CollectionContainsOnlyCheck(elements),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> namedContains(
        element: E,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = CollectionContainsCheck(element),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedDistinct(violationFactory: NamedViolationFactory<C>): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = CollectionDistinctCheck,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedMaxSize(
        maxSize: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = CollectionMaxSizeCheck(maxSize),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedMinSize(
        minSize: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = CollectionMinSizeCheck(minSize),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> namedNotContains(
        element: E,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = CollectionNotContainsCheck(element),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedNotEmpty(violationFactory: NamedViolationFactory<C>): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = CollectionNotEmptyCheck,
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedNotOfSize(
        size: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = CollectionNotOfSizeCheck(size),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedOfSize(
        size: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = CollectionOfSizeCheck(size),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedSizeBetween(
        sizeRange: IntRange,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = CollectionSizeBetweenCheck(sizeRange),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedSizeNotBetween(
        sizeRange: IntRange,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = CollectionSizeNotBetweenCheck(sizeRange),
            violationFactory = violationFactory,
        )
}
