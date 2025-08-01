package io.github.kverify.named.rule.set.collection

import io.github.kverify.check.set.collection.CollectionContainsNoneCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider

public class NamedCollectionContainsNoneRule<E, C : Collection<E>>(
    public val elements: Collection<E>,
    public val violationFactory: NamedViolationFactory<C> =
        NamedCollectionViolationFactoryProvider.Default.namedContainsNone(
            elements = elements,
        ),
) : NamedRule<C> by NamedPredicateRule(
        validationCheck = CollectionContainsNoneCheck(elements),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionContainsNoneRule(
    vararg elements: E,
    violationFactory: NamedViolationFactory<C>,
): NamedCollectionContainsNoneRule<E, C> =
    NamedCollectionContainsNoneRule(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionContainsNoneRule(vararg elements: E): NamedCollectionContainsNoneRule<E, C> {
    val elementsSet = elements.toSet()

    return NamedCollectionContainsNoneRule(
        elements = elementsSet,
    )
}
