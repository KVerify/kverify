package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringUpperCaseCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringUpperCaseRule(
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedUpperCase(),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck = StringUpperCaseCheck,
        violationFactory = violationFactory,
    )
