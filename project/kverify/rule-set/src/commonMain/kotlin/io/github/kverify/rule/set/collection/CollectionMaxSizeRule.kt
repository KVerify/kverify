package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionMaxSizeCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionMaxSizeRule<C : Collection<*>>(
    public val maxSize: Int,
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.maxSize(
            maxSize = maxSize,
        ),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionMaxSizeCheck(maxSize),
        violationFactory = violationFactory,
    )
