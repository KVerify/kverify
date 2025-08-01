package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringLowerCaseCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringLowerCaseRule(
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedLowerCase(),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck = StringLowerCaseCheck,
        violationFactory = violationFactory,
    )
