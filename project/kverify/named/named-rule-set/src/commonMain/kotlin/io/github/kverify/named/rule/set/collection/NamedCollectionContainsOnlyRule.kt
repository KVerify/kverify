package io.github.kverify.named.rule.set.collection

import io.github.kverify.check.set.collection.CollectionContainsOnlyCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider

public class NamedCollectionContainsOnlyRule<E, C : Collection<E>>(
    public val elements: Collection<E>,
    public val violationFactory: NamedViolationFactory<C> =
        NamedCollectionViolationFactoryProvider.Default.namedContainsOnly(
            elements = elements,
        ),
) : NamedRule<C> by NamedPredicateRule(
        validationCheck = CollectionContainsOnlyCheck(elements),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionContainsOnlyRule(
    element: E,
    violationFactory: NamedViolationFactory<C>,
): NamedCollectionContainsOnlyRule<E, C> =
    NamedCollectionContainsOnlyRule(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionContainsOnlyRule(
    vararg elements: E,
    violationFactory: NamedViolationFactory<C>,
): NamedCollectionContainsOnlyRule<E, C> =
    NamedCollectionContainsOnlyRule(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionContainsOnlyRule(element: E): NamedCollectionContainsOnlyRule<E, C> =
    NamedCollectionContainsOnlyRule(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionContainsOnlyRule(vararg elements: E): NamedCollectionContainsOnlyRule<E, C> =
    NamedCollectionContainsOnlyRule(
        elements = elements.asList(),
    )
