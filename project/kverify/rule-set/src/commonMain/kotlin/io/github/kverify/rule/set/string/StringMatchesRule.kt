package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringMatchesCheck
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringMatchesRule(
    public val regex: Regex,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.matches(
            regex = regex,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringMatchesCheck(regex),
        violationFactory = violationFactory,
    )
