package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringAlphabeticCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringAlphabeticRule(
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.alphabetic(),
) : Rule<String> by PredicateRule(
        validationCheck = StringAlphabeticCheck,
        violationFactory = violationFactory,
    )
