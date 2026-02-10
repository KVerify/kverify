package io.github.kverify.rule.set.comparable

import io.github.kverify.check.set.comparable.AtMostCheck
import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ViolationFactory
import io.github.kverify.violation.factory.set.comparable.AtMostViolationFactory

public class AtMostRule<T : Comparable<T>>(
    public val max: T,
    violationFactory: ViolationFactory<T> = AtMostViolationFactory(max),
) : PredicateRule<T>(
        validationCheck = AtMostCheck(max),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> AtMostRule(
    max: T,
    reason: String,
): AtMostRule<T> =
    AtMostRule(
        max = max,
        violationFactory =
            AtMostViolationFactory(
                max = max,
                reason = reason,
            ),
    )
