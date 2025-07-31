package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringMatchesCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public open class StringMatchesRule(
    public val regex: Regex,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.matches(
            regex = regex,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringMatchesCheck(regex),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringMatchesRule(
    stringRegex: String,
    violationFactory: ViolationFactory<String>,
): StringMatchesRule =
    StringMatchesRule(
        regex = stringRegex.toRegex(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringMatchesRule(stringRegex: String): StringMatchesRule =
    StringMatchesRule(
        regex = stringRegex.toRegex(),
    )
