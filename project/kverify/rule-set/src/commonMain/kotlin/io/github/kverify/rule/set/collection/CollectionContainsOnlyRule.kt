package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionContainsOnlyCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionContainsOnlyRule<E, C : Collection<E>>(
    public val elements: Collection<E>,
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.containsOnly(
            elements = elements,
        ),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionContainsOnlyCheck(elements),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsOnlyRule(
    element: E,
    violationFactory: ViolationFactory<C>,
): CollectionContainsOnlyRule<E, C> =
    CollectionContainsOnlyRule(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsOnlyRule(
    vararg elements: E,
    violationFactory: ViolationFactory<C>,
): CollectionContainsOnlyRule<E, C> =
    CollectionContainsOnlyRule(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsOnlyRule(element: E): CollectionContainsOnlyRule<E, C> =
    CollectionContainsOnlyRule(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsOnlyRule(vararg elements: E): CollectionContainsOnlyRule<E, C> =
    CollectionContainsOnlyRule(
        elements = elements.asList(),
    )
