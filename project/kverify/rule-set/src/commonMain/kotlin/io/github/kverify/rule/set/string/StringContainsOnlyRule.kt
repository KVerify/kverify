package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringContainsOnlyCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public open class StringContainsOnlyRule(
    public val chars: Iterable<Char>,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.containsOnly(
            chars = chars,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringContainsOnlyCheck(chars),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsOnlyRule(
    charsAsString: String,
    violationFactory: ViolationFactory<String>,
): StringContainsOnlyRule =
    StringContainsOnlyRule(
        chars = charsAsString.asIterable(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsOnlyRule(charsAsString: String): StringContainsOnlyRule =
    StringContainsOnlyRule(
        chars = charsAsString.asIterable(),
    )
