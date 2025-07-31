package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionOfSizeCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionOfSizeRule<C : Collection<*>>(
    public val size: Int,
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.ofSize(
            size = size,
        ),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionOfSizeCheck(size),
        violationFactory = violationFactory,
    )
