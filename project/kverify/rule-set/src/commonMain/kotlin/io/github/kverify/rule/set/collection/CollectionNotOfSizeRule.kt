package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionNotOfSizeCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionNotOfSizeRule<C : Collection<*>>(
    public val size: Int,
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.notOfSize(
            size = size,
        ),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionNotOfSizeCheck(size),
        violationFactory = violationFactory,
    )
