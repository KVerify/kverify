package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringContainsOnlyCheck
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringContainsOnlyRule(
    public val chars: Iterable<Char>,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.containsOnly(
            chars = chars,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringContainsOnlyCheck(chars),
        violationFactory = violationFactory,
    )
