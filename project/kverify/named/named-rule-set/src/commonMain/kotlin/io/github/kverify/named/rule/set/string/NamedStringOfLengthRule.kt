package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringOfLengthCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringOfLengthRule(
    public val length: Int,
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedOfLength(
            length = length,
        ),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck =
            StringOfLengthCheck(
                length = length,
            ),
        violationFactory = violationFactory,
    )
