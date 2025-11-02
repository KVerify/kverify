package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionNotEmptyCheck
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionNotEmptyRule<C : Collection<*>>(
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.notEmpty(),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionNotEmptyCheck,
        violationFactory = violationFactory,
    ) {
    public companion object : Rule<Collection<*>> by CollectionNotEmptyRule()
}
