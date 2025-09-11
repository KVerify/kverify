package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringNotBlankCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringNotBlankRule(
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.notBlank(),
) : Rule<String> by PredicateRule(
        validationCheck = StringNotBlankCheck,
        violationFactory = violationFactory,
    ) {
    public companion object : Rule<String> by StringNotBlankRule()
}
