package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringNotBlankCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringNotBlankRule(
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedNotBlank(),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck = StringNotBlankCheck,
        violationFactory = violationFactory,
    )
