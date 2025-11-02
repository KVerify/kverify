package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringContainsNoneCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringContainsNoneRule(
    public val chars: Iterable<Char>,
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedContainsNone(
            chars = chars,
        ),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck =
            StringContainsNoneCheck(
                chars = chars,
            ),
        violationFactory = violationFactory,
    )
