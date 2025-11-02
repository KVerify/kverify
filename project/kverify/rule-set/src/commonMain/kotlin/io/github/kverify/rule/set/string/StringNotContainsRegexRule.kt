package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringNotContainsRegexCheck
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringNotContainsRegexRule(
    public val regex: Regex,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.notContainsRegex(
            regex = regex,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringNotContainsRegexCheck(regex),
        violationFactory = violationFactory,
    )
