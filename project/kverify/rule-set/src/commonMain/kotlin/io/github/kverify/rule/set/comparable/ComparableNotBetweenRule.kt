package io.github.kverify.rule.set.comparable

import io.github.kverify.check.set.comparable.ComparableNotBetweenCheck
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.ComparableViolationFactoryProvider

public class ComparableNotBetweenRule<T : Comparable<T>>(
    public val range: ClosedRange<T>,
    public val violationFactory: ViolationFactory<T> =
        ComparableViolationFactoryProvider.Default.notBetween(
            range = range,
        ),
) : Rule<T> by PredicateRule(
        validationCheck = ComparableNotBetweenCheck(range),
        violationFactory = violationFactory,
    )
