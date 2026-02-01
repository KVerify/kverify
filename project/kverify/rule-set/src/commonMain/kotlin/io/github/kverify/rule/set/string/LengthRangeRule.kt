package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.LengthRangeCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.set.string.LengthRangeViolationFactory

public class LengthRangeRule(
    public val min: Int,
    public val max: Int,
    violationFactory: ViolationFactory<String> = LengthRangeViolationFactory(min, max),
) : PredicateRule<String>(
        validationCheck = LengthRangeCheck(min, max),
        violationFactory = violationFactory,
    )
