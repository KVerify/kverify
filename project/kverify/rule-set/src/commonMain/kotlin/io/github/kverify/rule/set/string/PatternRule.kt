package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.PatternCheck
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.violation.factory.set.string.PatternViolationFactory

public class PatternRule(
    public val regex: Regex,
    violationFactory: ViolationFactory<String> = PatternViolationFactory(regex),
) : PredicateRule<String>(
        validationCheck = PatternCheck(regex),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun PatternRule(
    regex: Regex,
    reason: String,
): PatternRule =
    PatternRule(
        regex = regex,
        violationFactory =
            PatternViolationFactory(
                regex = regex,
                reason = reason,
            ),
    )
