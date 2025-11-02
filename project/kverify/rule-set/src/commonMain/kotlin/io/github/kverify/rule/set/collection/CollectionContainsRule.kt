package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionContainsCheck
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionContainsRule<E, C : Collection<E>>(
    public val element: E,
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.contains(
            element = element,
        ),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionContainsCheck(element),
        violationFactory = violationFactory,
    )
