package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.SizeRangeCheck
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.violation.factory.set.collection.SizeRangeViolationFactory

public class SizeRangeRule<C : Collection<*>>(
    public val min: Int,
    public val max: Int,
    violationFactory: ViolationFactory<C> = SizeRangeViolationFactory(min, max),
) : PredicateRule<C>(
        validationCheck = SizeRangeCheck(min, max),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun SizeRangeRule(
    min: Int,
    max: Int,
    reason: String,
): SizeRangeRule<Collection<*>> =
    SizeRangeRule(
        min = min,
        max = max,
        violationFactory =
            SizeRangeViolationFactory(
                min = min,
                max = max,
                reason = reason,
            ),
    )
