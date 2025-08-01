package io.github.kverify.named.rule.set.collection

import io.github.kverify.check.set.collection.CollectionMaxSizeCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider

public class NamedCollectionMaxSizeRule<C : Collection<*>>(
    public val maxSize: Int,
    public val violationFactory: NamedViolationFactory<C> =
        NamedCollectionViolationFactoryProvider.Default.namedMaxSize(
            maxSize = maxSize,
        ),
) : NamedRule<C> by NamedPredicateRule(
        validationCheck = CollectionMaxSizeCheck(maxSize),
        violationFactory = violationFactory,
    )
