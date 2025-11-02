package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringContainsAllCheck
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringContainsAllRule(
    public val chars: Iterable<Char>,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.containsAll(
            chars = chars,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringContainsAllCheck(chars),
        violationFactory = violationFactory,
    )
