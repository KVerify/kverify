package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringEndsWithCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringEndsWithRule(
    public val suffix: String,
    public val ignoreCase: Boolean = false,
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedEndsWith(
            suffix = suffix,
            ignoreCase = ignoreCase,
        ),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck =
            StringEndsWithCheck(
                suffix = suffix,
                ignoreCase = ignoreCase,
            ),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringEndsWithRule(
    char: Char,
    ignoreCase: Boolean = false,
    violationFactory: NamedViolationFactory<String>,
): NamedStringEndsWithRule =
    NamedStringEndsWithRule(
        suffix = char.toString(),
        ignoreCase = ignoreCase,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringEndsWithRule(
    char: Char,
    ignoreCase: Boolean = false,
): NamedStringEndsWithRule =
    NamedStringEndsWithRule(
        suffix = char.toString(),
        ignoreCase = ignoreCase,
    )
