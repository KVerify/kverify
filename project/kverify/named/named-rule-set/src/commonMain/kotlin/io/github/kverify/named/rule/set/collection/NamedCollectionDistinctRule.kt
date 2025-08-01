package io.github.kverify.named.rule.set.collection

import io.github.kverify.check.set.collection.CollectionDistinctCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider

public class NamedCollectionDistinctRule<C : Collection<*>>(
    public val violationFactory: NamedViolationFactory<C> =
        NamedCollectionViolationFactoryProvider.Default.namedDistinct(),
) : NamedRule<C> by NamedPredicateRule(
        validationCheck = CollectionDistinctCheck,
        violationFactory = violationFactory,
    )
