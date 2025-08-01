package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringNumericCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringNumericRule(
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedNumeric(),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck = StringNumericCheck,
        violationFactory = violationFactory,
    )
