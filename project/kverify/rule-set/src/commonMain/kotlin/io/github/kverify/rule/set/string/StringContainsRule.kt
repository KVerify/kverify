package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringContainsCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringContainsRule(
    public val substring: String,
    public val ignoreCase: Boolean = false,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.contains(
            substring = substring,
            ignoreCase = ignoreCase,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringContainsCheck(substring, ignoreCase),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsRule(
    char: Char,
    ignoreCase: Boolean = false,
    violationFactory: ViolationFactory<String>,
): StringContainsRule =
    StringContainsRule(
        substring = char.toString(),
        ignoreCase = ignoreCase,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsRule(
    char: Char,
    ignoreCase: Boolean = false,
): StringContainsRule =
    StringContainsRule(
        substring = char.toString(),
        ignoreCase = ignoreCase,
    )
