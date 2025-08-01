package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringContainsRegexCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringContainsRegexRule(
    public val regex: Regex,
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedContainsRegex(
            regex = regex,
        ),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck =
            StringContainsRegexCheck(
                regex = regex,
            ),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsRegexRule(
    stringRegex: String,
    violationFactory: NamedViolationFactory<String>,
): NamedStringContainsRegexRule =
    NamedStringContainsRegexRule(
        regex = stringRegex.toRegex(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsRegexRule(stringRegex: String): NamedStringContainsRegexRule =
    NamedStringContainsRegexRule(
        regex = stringRegex.toRegex(),
    )
