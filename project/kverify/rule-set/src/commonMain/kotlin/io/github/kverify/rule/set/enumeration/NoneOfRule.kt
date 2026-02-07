package io.github.kverify.rule.set.enumeration

import io.github.kverify.check.set.enumeration.NoneOfCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.set.enumeration.NoneOfViolationFactory

public class NoneOfRule<T>(
    public val forbidden: Set<T>,
    violationFactory: ViolationFactory<T> = NoneOfViolationFactory(forbidden),
) : PredicateRule<T>(
        validationCheck = NoneOfCheck(forbidden),
        violationFactory = violationFactory,
    )
