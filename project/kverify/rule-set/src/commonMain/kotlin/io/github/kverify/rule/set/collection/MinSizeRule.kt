package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.MinSizeCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.set.collection.MinSizeViolationFactory

public class MinSizeRule<C : Collection<*>>(
    public val min: Int,
    violationFactory: ViolationFactory<C> = MinSizeViolationFactory(min),
) : PredicateRule<C>(
        validationCheck = MinSizeCheck(min),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun MinSizeRule(
    min: Int,
    reason: String,
): MinSizeRule<Collection<*>> =
    MinSizeRule(
        min = min,
        violationFactory =
            MinSizeViolationFactory(
                min = min,
                reason = reason,
            ),
    )
