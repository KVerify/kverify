package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringUpperCaseCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringUpperCaseRule(
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.upperCase(),
) : Rule<String> by PredicateRule(
        validationCheck = StringUpperCaseCheck,
        violationFactory = violationFactory,
    ) {
    public companion object : Rule<String> by StringUpperCaseRule()
}
