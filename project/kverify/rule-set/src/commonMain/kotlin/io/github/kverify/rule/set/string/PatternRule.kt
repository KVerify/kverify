package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.PatternCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.set.string.PatternViolationFactory

public class PatternRule(
    public val regex: Regex,
    violationFactory: ViolationFactory<String> = PatternViolationFactory(regex),
) : PredicateRule<String>(
        validationCheck = PatternCheck(regex),
        violationFactory = violationFactory,
    )
