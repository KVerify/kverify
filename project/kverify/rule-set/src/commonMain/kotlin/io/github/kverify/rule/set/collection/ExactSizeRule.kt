package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.ExactSizeCheck
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.violation.factory.set.collection.ExactSizeViolationFactory

public class ExactSizeRule<C : Collection<*>>(
    public val size: Int,
    violationFactory: ViolationFactory<C> = ExactSizeViolationFactory(size),
) : PredicateRule<C>(
        validationCheck = ExactSizeCheck(size),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun ExactSizeRule(
    size: Int,
    reason: String,
): ExactSizeRule<Collection<*>> =
    ExactSizeRule(
        size = size,
        violationFactory =
            ExactSizeViolationFactory(
                size = size,
                reason = reason,
            ),
    )
