package io.github.kverify.rule.set.comparable

import io.github.kverify.check.set.comparable.ComparableNotEqualToCheck
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.ComparableViolationFactoryProvider

public class ComparableNotEqualToRule<T : Comparable<T>>(
    public val other: T,
    public val violationFactory: ViolationFactory<T> =
        ComparableViolationFactoryProvider.Default.notEqualTo(other),
) : Rule<T> by PredicateRule(
        validationCheck = ComparableNotEqualToCheck(other),
        violationFactory = violationFactory,
    )
