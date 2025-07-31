package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionSizeBetweenCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionSizeBetweenRule<C : Collection<*>>(
    public val range: IntRange,
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.sizeBetween(
            range = range,
        ),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionSizeBetweenCheck(range),
        violationFactory = violationFactory,
    )
