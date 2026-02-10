package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.ExactLengthCheck
import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ViolationFactory
import io.github.kverify.violation.factory.set.string.ExactLengthViolationFactory

public class ExactLengthRule(
    public val length: Int,
    violationFactory: ViolationFactory<String> = ExactLengthViolationFactory(length),
) : PredicateRule<String>(
        validationCheck = ExactLengthCheck(length),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun ExactLengthRule(
    length: Int,
    reason: String,
): ExactLengthRule =
    ExactLengthRule(
        length = length,
        violationFactory =
            ExactLengthViolationFactory(
                length = length,
                reason = reason,
            ),
    )
