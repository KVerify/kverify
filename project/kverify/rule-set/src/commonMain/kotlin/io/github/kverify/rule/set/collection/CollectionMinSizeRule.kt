package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionMinSizeCheck
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionMinSizeRule<C : Collection<*>>(
    public val minSize: Int,
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.minSize(
            minSize = minSize,
        ),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionMinSizeCheck(minSize),
        violationFactory = violationFactory,
    )
