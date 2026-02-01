package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.MaxLengthCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.set.string.MaxLengthViolationFactory

public class MaxLengthRule(
    public val max: Int,
    violationFactory: ViolationFactory<String> = MaxLengthViolationFactory(max),
) : PredicateRule<String>(
        validationCheck = MaxLengthCheck(max),
        violationFactory = violationFactory,
    )
