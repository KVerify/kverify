package io.github.kverify.rule.set.comparable

import io.github.kverify.check.set.comparable.ComparableBetweenCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.ComparableViolationFactoryProvider

public class ComparableBetweenRule<T : Comparable<T>>(
    public val range: ClosedRange<T>,
    public val violationFactory: ViolationFactory<T> =
        ComparableViolationFactoryProvider.Default.between(
            range = range,
        ),
) : Rule<T> by PredicateRule(
        validationCheck = ComparableBetweenCheck(range),
        violationFactory = violationFactory,
    )
