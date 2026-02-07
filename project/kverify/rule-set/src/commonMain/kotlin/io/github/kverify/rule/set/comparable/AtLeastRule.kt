package io.github.kverify.rule.set.comparable

import io.github.kverify.check.set.comparable.AtLeastCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.set.comparable.AtLeastViolationFactory

public class AtLeastRule<T : Comparable<T>>(
    public val min: T,
    violationFactory: ViolationFactory<T> = AtLeastViolationFactory(min),
) : PredicateRule<T>(
        validationCheck = AtLeastCheck(min),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> AtLeastRule(
    min: T,
    reason: String,
): AtLeastRule<T> =
    AtLeastRule(
        min = min,
        violationFactory =
            AtLeastViolationFactory(
                min = min,
                reason = reason,
            ),
    )
