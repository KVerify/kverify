package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.MaxLengthCheck
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.violation.factory.set.string.MaxLengthViolationFactory

public class MaxLengthRule(
    public val max: Int,
    violationFactory: ViolationFactory<String> = MaxLengthViolationFactory(max),
) : PredicateRule<String>(
        validationCheck = MaxLengthCheck(max),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun MaxLengthRule(
    max: Int,
    reason: String,
): MaxLengthRule =
    MaxLengthRule(
        max = max,
        violationFactory =
            MaxLengthViolationFactory(
                max = max,
                reason = reason,
            ),
    )
