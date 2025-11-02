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
