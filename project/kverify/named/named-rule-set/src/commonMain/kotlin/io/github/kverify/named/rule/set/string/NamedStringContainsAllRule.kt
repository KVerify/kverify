package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringContainsAllCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringContainsAllRule(
    public val chars: Iterable<Char>,
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedContainsAll(
            chars = chars,
        ),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck =
            StringContainsAllCheck(
                chars = chars,
            ),
        violationFactory = violationFactory,
    )
