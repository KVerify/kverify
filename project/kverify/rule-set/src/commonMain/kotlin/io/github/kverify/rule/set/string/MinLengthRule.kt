package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.MinLengthCheck
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.violation.factory.set.string.MinLengthViolationFactory

public class MinLengthRule(
    public val min: Int,
    violationFactory: ViolationFactory<String> = MinLengthViolationFactory(min),
) : PredicateRule<String>(
        validationCheck = MinLengthCheck(min),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun MinLengthRule(
    min: Int,
    reason: String,
): MinLengthRule =
    MinLengthRule(
        min = min,
        violationFactory =
            MinLengthViolationFactory(
                min = min,
                reason = reason,
            ),
    )
