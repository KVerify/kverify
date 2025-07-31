package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringStartsWithCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public open class StringStartsWithRule(
    public val prefix: String,
    public val ignoreCase: Boolean = false,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.startsWith(
            prefix = prefix,
            ignoreCase = ignoreCase,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringStartsWithCheck(prefix, ignoreCase),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringStartsWithRule(
    prefixChar: Char,
    ignoreCase: Boolean = false,
    violationFactory: ViolationFactory<String>,
): StringStartsWithRule =
    StringStartsWithRule(
        prefix = prefixChar.toString(),
        ignoreCase = ignoreCase,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringStartsWithRule(
    prefixChar: Char,
    ignoreCase: Boolean = false,
): StringStartsWithRule =
    StringStartsWithRule(
        prefix = prefixChar.toString(),
        ignoreCase = ignoreCase,
    )
