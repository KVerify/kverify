package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionSizeBetweenCheck
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionSizeBetweenRule<C : Collection<*>>(
    public val sizeRange: IntRange,
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.sizeBetween(
            sizeRange = sizeRange,
        ),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionSizeBetweenCheck(sizeRange),
        violationFactory = violationFactory,
    )
