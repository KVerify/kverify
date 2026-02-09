package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.MaxSizeCheck
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.violation.factory.set.collection.MaxSizeViolationFactory

public class MaxSizeRule<C : Collection<*>>(
    public val max: Int,
    violationFactory: ViolationFactory<C> = MaxSizeViolationFactory(max),
) : PredicateRule<C>(
        validationCheck = MaxSizeCheck(max),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun MaxSizeRule(
    max: Int,
    reason: String,
): MaxSizeRule<Collection<*>> =
    MaxSizeRule(
        max = max,
        violationFactory =
            MaxSizeViolationFactory(
                max = max,
                reason = reason,
            ),
    )
