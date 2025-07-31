package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringContainsRegexCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public open class StringContainsRegexRule(
    public val regex: Regex,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.containsRegex(
            regex = regex,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringContainsRegexCheck(regex),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsRegexRule(
    stringRegex: String,
    violationFactory: ViolationFactory<String>,
): StringContainsRegexRule =
    StringContainsRegexRule(
        regex = stringRegex.toRegex(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsRegexRule(stringRegex: String): StringContainsRegexRule =
    StringContainsRegexRule(
        regex = stringRegex.toRegex(),
    )
