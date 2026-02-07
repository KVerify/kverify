package io.github.kverify.rule.set.comparable

import io.github.kverify.check.set.comparable.EqualToCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.set.comparable.EqualToViolationFactory

public class EqualToRule<T : Comparable<T>>(
    public val expected: T,
    violationFactory: ViolationFactory<T> = EqualToViolationFactory(expected),
) : PredicateRule<T>(
        validationCheck = EqualToCheck(expected),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> EqualToRule(
    expected: T,
    reason: String,
): EqualToRule<T> =
    EqualToRule(
        expected = expected,
        violationFactory =
            EqualToViolationFactory(
                expected = expected,
                reason = reason,
            ),
    )
