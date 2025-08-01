package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringAlphabeticCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringAlphanumericRule(
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedAlphanumeric(),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck = StringAlphabeticCheck,
        violationFactory = violationFactory,
    )
