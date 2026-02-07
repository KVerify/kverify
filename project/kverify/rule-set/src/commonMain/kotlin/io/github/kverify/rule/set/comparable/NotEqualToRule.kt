package io.github.kverify.rule.set.comparable

import io.github.kverify.check.set.comparable.NotEqualToCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.set.comparable.NotEqualToViolationFactory

public class NotEqualToRule<T : Comparable<T>>(
    public val forbidden: T,
    violationFactory: ViolationFactory<T> = NotEqualToViolationFactory(forbidden),
) : PredicateRule<T>(
        validationCheck = NotEqualToCheck(forbidden),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> NotEqualToRule(
    forbidden: T,
    reason: String,
): NotEqualToRule<T> =
    NotEqualToRule(
        forbidden = forbidden,
        violationFactory =
            NotEqualToViolationFactory(
                forbidden = forbidden,
                reason = reason,
            ),
    )
