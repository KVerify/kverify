package io.github.kverify.named.rule.set.comparable

import io.github.kverify.check.set.comparable.ComparableGreaterThanOrEqualToCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedComparableViolationFactoryProvider

public class NamedComparableGreaterThanOrEqualToRule<T : Comparable<T>>(
    public val other: T,
    public val violationFactory: NamedViolationFactory<T> =
        NamedComparableViolationFactoryProvider.Default.namedGreaterThanOrEqualTo(
            other = other,
        ),
) : NamedRule<T> by NamedPredicateRule(
        validationCheck = ComparableGreaterThanOrEqualToCheck(other),
        violationFactory = violationFactory,
    )
