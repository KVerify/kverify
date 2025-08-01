package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringMatchesCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringMatchesRule(
    public val regex: Regex,
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedMatches(
            regex = regex,
        ),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck =
            StringMatchesCheck(
                regex = regex,
            ),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringMatchesRule(
    stringRegex: String,
    violationFactory: NamedViolationFactory<String>,
): NamedStringMatchesRule =
    NamedStringMatchesRule(
        regex = stringRegex.toRegex(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringMatchesRule(stringRegex: String): NamedStringMatchesRule =
    NamedStringMatchesRule(
        regex = stringRegex.toRegex(),
    )
