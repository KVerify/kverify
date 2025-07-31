package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionDistinctCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionDistinctRule<C : Collection<*>>(
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.distinct(),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionDistinctCheck,
        violationFactory = violationFactory,
    )
