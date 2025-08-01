package io.github.kverify.named.rule.set.collection

import io.github.kverify.check.set.collection.CollectionContainsCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider

public class NamedCollectionContainsRule<E, C : Collection<E>>(
    public val element: E,
    public val violationFactory: NamedViolationFactory<C> =
        NamedCollectionViolationFactoryProvider.Default.namedContains(
            element = element,
        ),
) : NamedRule<C> by NamedPredicateRule(
        validationCheck = CollectionContainsCheck(element),
        violationFactory = violationFactory,
    )
