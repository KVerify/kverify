package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionContainsNoneCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionContainsNoneRule<E, C : Collection<E>>(
    public val elements: Collection<E>,
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.containsNone(
            elements = elements,
        ),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionContainsNoneCheck(elements),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsNoneRule(
    element: E,
    violationFactory: ViolationFactory<C>,
): CollectionContainsNoneRule<E, C> =
    CollectionContainsNoneRule(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsNoneRule(
    vararg elements: E,
    violationFactory: ViolationFactory<C>,
): CollectionContainsNoneRule<E, C> =
    CollectionContainsNoneRule(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsNoneRule(element: E): CollectionContainsNoneRule<E, C> =
    CollectionContainsNoneRule(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsNoneRule(vararg elements: E): CollectionContainsNoneRule<E, C> =
    CollectionContainsNoneRule(
        elements = elements.asList(),
    )
