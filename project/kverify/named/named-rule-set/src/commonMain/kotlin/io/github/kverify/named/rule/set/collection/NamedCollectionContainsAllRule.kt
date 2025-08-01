package io.github.kverify.named.rule.set.collection

import io.github.kverify.check.set.collection.CollectionContainsAllCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider

public class NamedCollectionContainsAllRule<E, C : Collection<E>>(
    public val elements: Collection<E>,
    public val violationFactory: NamedViolationFactory<C> =
        NamedCollectionViolationFactoryProvider.Default.namedContainsAll(
            elements = elements,
        ),
) : NamedRule<C> by NamedPredicateRule(
        validationCheck = CollectionContainsAllCheck(elements),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionContainsAllRule(
    vararg elements: E,
    violationFactory: NamedViolationFactory<C>,
): NamedCollectionContainsAllRule<E, C> =
    NamedCollectionContainsAllRule(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionContainsAllRule(vararg elements: E): NamedCollectionContainsAllRule<E, C> {
    val elementsSet = elements.toSet()

    return NamedCollectionContainsAllRule(
        elements = elementsSet,
    )
}
