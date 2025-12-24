package io.github.kverify.named.rule.set.provider

import io.github.kverify.check.set.provider.CollectionCheckProvider
import io.github.kverify.check.set.provider.DefaultCollectionCheckProvider
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.set.provider.CollectionViolationProvider
import io.github.kverify.violation.set.provider.DefaultCollectionViolationProvider

@Suppress("TooManyFunctions")
public class DefaultNamedCollectionRuleProvider(
    public val collectionCheckProvider: CollectionCheckProvider = DefaultCollectionCheckProvider(),
    override val collectionViolationProvider: CollectionViolationProvider = CollectionViolationProvider.Default,
) : NamedCollectionRuleProvider {
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
            validationCheck = collectionCheckProvider.containsAll(elements),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> namedContainsNone(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = collectionCheckProvider.containsNone(elements),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> namedContainsOnly(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = collectionCheckProvider.containsOnly(elements),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> namedContains(
        element: E,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = collectionCheckProvider.contains(element),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedDistinct(violationFactory: NamedViolationFactory<C>): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = collectionCheckProvider.distinct(),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedMaxSize(
        maxSize: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = collectionCheckProvider.maxSize(maxSize),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedMinSize(
        minSize: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = collectionCheckProvider.minSize(minSize),
            violationFactory = violationFactory,
        )

    override fun <E, C : Collection<E>> namedNotContains(
        element: E,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = collectionCheckProvider.notContains(element),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedNotEmpty(violationFactory: NamedViolationFactory<C>): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = collectionCheckProvider.notEmpty(),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedNotOfSize(
        size: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = collectionCheckProvider.notOfSize(size),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedOfSize(
        size: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = collectionCheckProvider.ofSize(size),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedSizeBetween(
        sizeRange: IntRange,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = collectionCheckProvider.sizeBetween(sizeRange),
            violationFactory = violationFactory,
        )

    override fun <C : Collection<*>> namedSizeNotBetween(
        sizeRange: IntRange,
        violationFactory: NamedViolationFactory<C>,
    ): NamedPredicateRule<C> =
        NamedPredicateRule(
            validationCheck = collectionCheckProvider.sizeNotBetween(sizeRange),
            violationFactory = violationFactory,
        )
}
