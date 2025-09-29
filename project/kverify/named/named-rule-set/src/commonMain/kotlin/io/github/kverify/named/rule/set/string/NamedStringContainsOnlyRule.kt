package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringContainsNoneCheck
import io.github.kverify.check.set.string.StringContainsOnlyCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringContainsOnlyRule(
    public val chars: Iterable<Char>,
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedContainsOnly(
            chars = chars,
        ),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck =
            StringContainsOnlyCheck(
                chars = chars,
            ),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsOnlyRule(
    vararg chars: Char,
    violationFactory: NamedViolationFactory<String>,
): NamedStringContainsOnlyRule =
    NamedStringContainsOnlyRule(
        chars = chars.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsOnlyRule(vararg chars: Char): NamedStringContainsOnlyRule =
    NamedStringContainsOnlyRule(
        chars = chars.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsOnlyRule(
    string: String,
    violationFactory: NamedViolationFactory<String>,
): NamedStringContainsOnlyRule =
    NamedStringContainsOnlyRule(
        chars = string.asIterable(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsOnlyRule(string: String): NamedStringContainsOnlyRule =
    NamedStringContainsOnlyRule(
        chars = string.asIterable(),
    )
