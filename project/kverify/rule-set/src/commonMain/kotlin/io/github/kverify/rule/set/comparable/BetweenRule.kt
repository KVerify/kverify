package io.github.kverify.rule.set.comparable

import io.github.kverify.check.set.comparable.BetweenCheck
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.violation.factory.set.comparable.BetweenViolationFactory

public class BetweenRule<T : Comparable<T>>(
    public val min: T,
    public val max: T,
    violationFactory: ViolationFactory<T> = BetweenViolationFactory(min, max),
) : PredicateRule<T>(
        validationCheck = BetweenCheck(min, max),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> BetweenRule(
    min: T,
    max: T,
    reason: String,
): BetweenRule<T> =
    BetweenRule(
        min = min,
        max = max,
        violationFactory =
            BetweenViolationFactory(
                min = min,
                max = max,
                reason = reason,
            ),
    )
