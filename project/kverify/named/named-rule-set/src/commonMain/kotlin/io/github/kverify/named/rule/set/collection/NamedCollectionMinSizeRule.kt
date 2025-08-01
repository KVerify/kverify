package io.github.kverify.named.rule.set.collection

import io.github.kverify.check.set.collection.CollectionMinSizeCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider

public class NamedCollectionMinSizeRule<C : Collection<*>>(
    public val minSize: Int,
    public val violationFactory: NamedViolationFactory<C> =
        NamedCollectionViolationFactoryProvider.Default.namedMinSize(
            minSize = minSize,
        ),
) : NamedRule<C> by NamedPredicateRule(
        validationCheck = CollectionMinSizeCheck(minSize),
        violationFactory = violationFactory,
    )
