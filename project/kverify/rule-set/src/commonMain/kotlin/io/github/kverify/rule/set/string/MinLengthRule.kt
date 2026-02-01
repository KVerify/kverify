package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.MinLengthCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.set.string.MinLengthViolationFactory

public class MinLengthRule(
    public val min: Int,
    violationFactory: ViolationFactory<String> = MinLengthViolationFactory(min),
) : PredicateRule<String>(
        validationCheck = MinLengthCheck(min),
        violationFactory = violationFactory,
    )
