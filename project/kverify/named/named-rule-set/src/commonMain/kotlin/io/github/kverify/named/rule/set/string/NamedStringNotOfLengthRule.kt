package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringNotOfLengthCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringNotOfLengthRule(
    public val length: Int,
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedNotOfLength(
            length = length,
        ),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck =
            StringNotOfLengthCheck(
                length = length,
            ),
        violationFactory = violationFactory,
    )
