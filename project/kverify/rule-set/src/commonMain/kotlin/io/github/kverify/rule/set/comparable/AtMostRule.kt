package io.github.kverify.rule.set.comparable

import io.github.kverify.check.set.comparable.AtMostCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.set.comparable.AtMostViolationFactory

public class AtMostRule<T : Comparable<T>>(
    public val max: T,
    violationFactory: ViolationFactory<T> = AtMostViolationFactory(max),
) : PredicateRule<T>(
        validationCheck = AtMostCheck(max),
        violationFactory = violationFactory,
    )
