package io.github.kverify.named.rule.set.collection

import io.github.kverify.check.set.collection.CollectionNotOfSizeCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider

public class NamedCollectionNotOfSizeRule<C : Collection<*>>(
    public val size: Int,
    public val violationFactory: NamedViolationFactory<C> =
        NamedCollectionViolationFactoryProvider.Default.namedNotOfSize(
            size = size,
        ),
) : NamedRule<C> by NamedPredicateRule(
        validationCheck = CollectionNotOfSizeCheck(size),
        violationFactory = violationFactory,
    )
