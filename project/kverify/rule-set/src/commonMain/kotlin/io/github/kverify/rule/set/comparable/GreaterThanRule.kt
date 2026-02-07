package io.github.kverify.rule.set.comparable

import io.github.kverify.check.set.comparable.GreaterThanCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.set.comparable.GreaterThanViolationFactory

public class GreaterThanRule<T : Comparable<T>>(
    public val min: T,
    violationFactory: ViolationFactory<T> = GreaterThanViolationFactory(min),
) : PredicateRule<T>(
        validationCheck = GreaterThanCheck(min),
        violationFactory = violationFactory,
    )
