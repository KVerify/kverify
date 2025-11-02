package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionContainsAllCheck
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.rule.predicate.PredicateRule
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
