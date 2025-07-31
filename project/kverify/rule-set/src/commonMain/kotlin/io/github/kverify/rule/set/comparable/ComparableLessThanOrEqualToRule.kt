package io.github.kverify.rule.set.comparable

import io.github.kverify.check.set.comparable.ComparableLessThanOrEqualToCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.ComparableViolationFactoryProvider

public class ComparableLessThanOrEqualToRule<T : Comparable<T>>(
    public val other: T,
    public val violationFactory: ViolationFactory<T> =
        ComparableViolationFactoryProvider.Default.lessThanOrEqualTo(
            other = other,
        ),
) : Rule<T> by PredicateRule(
        validationCheck = ComparableLessThanOrEqualToCheck(other),
        violationFactory = violationFactory,
    )
