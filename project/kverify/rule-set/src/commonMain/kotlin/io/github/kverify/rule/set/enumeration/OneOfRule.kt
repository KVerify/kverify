package io.github.kverify.rule.set.enumeration

import io.github.kverify.check.set.enumeration.OneOfCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.set.enumeration.OneOfViolationFactory

public class OneOfRule<T>(
    public val allowed: Set<T>,
    violationFactory: ViolationFactory<T> = OneOfViolationFactory(allowed),
) : PredicateRule<T>(
        validationCheck = OneOfCheck(allowed),
        violationFactory = violationFactory,
    )
