package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionNotContainsCheck
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionNotContainsRule<E, C : Collection<E>>(
    public val element: E,
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.notContains(
            element = element,
        ),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionNotContainsCheck(element),
        violationFactory = violationFactory,
    )
