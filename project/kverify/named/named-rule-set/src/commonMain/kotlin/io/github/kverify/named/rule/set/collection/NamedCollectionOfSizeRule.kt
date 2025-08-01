package io.github.kverify.named.rule.set.collection

import io.github.kverify.check.set.collection.CollectionOfSizeCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider

public class NamedCollectionOfSizeRule<C : Collection<*>>(
    public val size: Int,
    public val violationFactory: NamedViolationFactory<C> =
        NamedCollectionViolationFactoryProvider.Default.namedOfSize(
            size = size,
        ),
) : NamedRule<C> by NamedPredicateRule(
        validationCheck = CollectionOfSizeCheck(size),
        violationFactory = violationFactory,
    )
