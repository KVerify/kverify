package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.DistinctCheck
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.violation.factory.set.collection.DistinctViolationFactory

public class DistinctRule<C : Collection<*>>(
    violationFactory: ViolationFactory<C> = DistinctViolationFactory(),
) : PredicateRule<C>(
        validationCheck = DistinctCheck,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun DistinctRule(reason: String): DistinctRule<Collection<*>> =
    DistinctRule(
        violationFactory =
            DistinctViolationFactory(
                reason = reason,
            ),
    )
