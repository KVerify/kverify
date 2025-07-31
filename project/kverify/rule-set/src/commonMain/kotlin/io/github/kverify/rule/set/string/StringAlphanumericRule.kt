package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringAlphanumericCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public open class StringAlphanumericRule(
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.alphanumeric(),
) : Rule<String> by PredicateRule(
        validationCheck = StringAlphanumericCheck,
        violationFactory = violationFactory,
    )
