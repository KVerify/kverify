package io.github.kverify.rule.set.comparable

import io.github.kverify.check.set.comparable.LessThanCheck
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.violation.factory.set.comparable.LessThanViolationFactory

public class LessThanRule<T : Comparable<T>>(
    public val max: T,
    violationFactory: ViolationFactory<T> = LessThanViolationFactory(max),
) : PredicateRule<T>(
        validationCheck = LessThanCheck(max),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> LessThanRule(
    max: T,
    reason: String,
): LessThanRule<T> =
    LessThanRule(
        max = max,
        violationFactory =
            LessThanViolationFactory(
                max = max,
                reason = reason,
            ),
    )
