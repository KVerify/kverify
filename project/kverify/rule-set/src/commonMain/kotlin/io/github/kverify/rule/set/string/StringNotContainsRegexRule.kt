package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringNotContainsRegexCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public open class StringNotContainsRegexRule(
    public val regex: Regex,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.notContainsRegex(
            regex = regex,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringNotContainsRegexCheck(regex),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringNotContainsRegexRule(
    regexAsString: String,
    violationFactory: ViolationFactory<String>,
): StringNotContainsRegexRule =
    StringNotContainsRegexRule(
        regex = regexAsString.toRegex(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringNotContainsRegexRule(regexAsString: String): StringNotContainsRegexRule =
    StringNotContainsRegexRule(
        regex = regexAsString.toRegex(),
    )
