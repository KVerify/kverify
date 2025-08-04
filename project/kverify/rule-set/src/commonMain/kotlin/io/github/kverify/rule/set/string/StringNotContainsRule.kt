package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringNotContainsCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringNotContainsRule(
    public val substring: String,
    public val ignoreCase: Boolean = false,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.notContains(
            substring = substring,
            ignoreCase = ignoreCase,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringNotContainsCheck(substring, ignoreCase),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringNotContainsRule(
    char: Char,
    ignoreCase: Boolean = false,
    violationFactory: ViolationFactory<String>,
): StringNotContainsRule =
    StringNotContainsRule(
        substring = char.toString(),
        ignoreCase = ignoreCase,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringNotContainsRule(
    char: Char,
    ignoreCase: Boolean = false,
): StringNotContainsRule =
    StringNotContainsRule(
        substring = char.toString(),
        ignoreCase = ignoreCase,
    )
