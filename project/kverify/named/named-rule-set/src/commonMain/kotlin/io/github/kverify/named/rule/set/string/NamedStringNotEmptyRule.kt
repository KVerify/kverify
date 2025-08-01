package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringNotEmptyCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringNotEmptyRule(
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedNotEmpty(),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck = StringNotEmptyCheck,
        violationFactory = violationFactory,
    )
