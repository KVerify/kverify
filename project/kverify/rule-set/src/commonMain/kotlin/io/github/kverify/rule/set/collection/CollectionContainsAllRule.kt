package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionContainsAllCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionContainsAllRule<E, C : Collection<E>>(
    public val elements: Collection<E>,
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.containsAll(
            elements = elements,
        ),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionContainsAllCheck(elements),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsAllRule(
    vararg elements: E,
    violationFactory: ViolationFactory<C>,
): CollectionContainsAllRule<E, C> {
    val elementsSet = setOf(elements = elements)

    return CollectionContainsAllRule(
        elements = elementsSet,
        violationFactory = violationFactory,
    )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsAllRule(vararg elements: E): CollectionContainsAllRule<E, C> {
    val elementsSet = setOf(elements = elements)

    return CollectionContainsAllRule(elements = elementsSet)
}
