package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringNumericCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public open class StringNumericRule(
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.numeric(),
) : Rule<String> by PredicateRule(
        validationCheck = StringNumericCheck,
        violationFactory = violationFactory,
    )
