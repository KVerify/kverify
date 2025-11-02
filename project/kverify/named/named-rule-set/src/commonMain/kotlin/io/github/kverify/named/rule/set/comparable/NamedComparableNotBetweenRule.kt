package io.github.kverify.named.rule.set.comparable

import io.github.kverify.check.set.comparable.ComparableNotBetweenCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedComparableViolationFactoryProvider

public class NamedComparableNotBetweenRule<T : Comparable<T>>(
    public val range: ClosedRange<T>,
    public val violationFactory: NamedViolationFactory<T> =
        NamedComparableViolationFactoryProvider.Default.namedNotBetween(
            range = range,
        ),
) : NamedRule<T> by NamedPredicateRule(
        validationCheck = ComparableNotBetweenCheck(range),
        violationFactory = violationFactory,
    )
