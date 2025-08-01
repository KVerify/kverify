package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringContainsCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringNotContainsRule(
    public val substring: String,
    public val ignoreCase: Boolean = false,
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedNotContains(
            substring = substring,
            ignoreCase = ignoreCase,
        ),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck =
            StringContainsCheck(
                substring = substring,
                ignoreCase = ignoreCase,
            ),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringNotContainsRule(
    char: Char,
    ignoreCase: Boolean = false,
    violationFactory: NamedViolationFactory<String>,
): NamedStringNotContainsRule =
    NamedStringNotContainsRule(
        substring = char.toString(),
        ignoreCase = ignoreCase,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringNotContainsRule(
    char: Char,
    ignoreCase: Boolean = false,
): NamedStringNotContainsRule =
    NamedStringNotContainsRule(
        substring = char.toString(),
        ignoreCase = ignoreCase,
    )
