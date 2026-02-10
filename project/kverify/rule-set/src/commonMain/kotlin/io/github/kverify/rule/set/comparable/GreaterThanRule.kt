package io.github.kverify.rule.set.comparable

import io.github.kverify.check.set.comparable.GreaterThanCheck
import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ViolationFactory
import io.github.kverify.violation.factory.set.comparable.GreaterThanViolationFactory

public class GreaterThanRule<T : Comparable<T>>(
    public val min: T,
    violationFactory: ViolationFactory<T> = GreaterThanViolationFactory(min),
) : PredicateRule<T>(
        validationCheck = GreaterThanCheck(min),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> GreaterThanRule(
    min: T,
    reason: String,
): GreaterThanRule<T> =
    GreaterThanRule(
        min = min,
        violationFactory =
            GreaterThanViolationFactory(
                min = min,
                reason = reason,
            ),
    )
