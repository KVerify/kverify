package io.github.kverify.rule.set.enumeration

import io.github.kverify.check.set.enumeration.NoneOfCheck
import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ViolationFactory
import io.github.kverify.violation.factory.set.enumeration.NoneOfViolationFactory

public class NoneOfRule<T>(
    public val forbidden: Set<T>,
    violationFactory: ViolationFactory<T> = NoneOfViolationFactory(forbidden),
) : PredicateRule<T>(
        validationCheck = NoneOfCheck(forbidden),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> NoneOfRule(
    forbidden: Set<T>,
    reason: String,
): NoneOfRule<T> =
    NoneOfRule(
        forbidden = forbidden,
        violationFactory =
            NoneOfViolationFactory(
                forbidden = forbidden,
                reason = reason,
            ),
    )
