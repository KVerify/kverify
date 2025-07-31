package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringContainsAllCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public open class StringContainsAllRule(
    public val chars: Iterable<Char>,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.containsAll(
            chars = chars,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringContainsAllCheck(chars),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsAllRule(
    charsAsString: String,
    violationGenerator: ViolationFactory<String>,
): StringContainsAllRule =
    StringContainsAllRule(
        chars = charsAsString.asIterable(),
        violationFactory = violationGenerator,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsAllRule(charsAsString: String): StringContainsAllRule =
    StringContainsAllRule(
        chars = charsAsString.asIterable(),
    )
