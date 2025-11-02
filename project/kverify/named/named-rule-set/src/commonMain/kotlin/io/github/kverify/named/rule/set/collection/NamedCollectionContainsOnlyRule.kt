package io.github.kverify.named.rule.set.collection

import io.github.kverify.check.set.collection.CollectionContainsOnlyCheck
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
