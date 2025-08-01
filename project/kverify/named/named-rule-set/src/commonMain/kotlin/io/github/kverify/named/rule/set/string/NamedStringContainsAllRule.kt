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

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsAllRule(
    vararg chars: Char,
    violationFactory: NamedViolationFactory<String>,
): NamedStringContainsAllRule =
    NamedStringContainsAllRule(
        chars = chars.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsAllRule(vararg chars: Char): NamedStringContainsAllRule =
    NamedStringContainsAllRule(
        chars = chars.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsAllRule(
    string: String,
    violationFactory: NamedViolationFactory<String>,
): NamedStringContainsAllRule =
    NamedStringContainsAllRule(
        chars = string.asIterable(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsAllRule(string: String): NamedStringContainsAllRule =
    NamedStringContainsAllRule(
        chars = string.asIterable(),
    )
