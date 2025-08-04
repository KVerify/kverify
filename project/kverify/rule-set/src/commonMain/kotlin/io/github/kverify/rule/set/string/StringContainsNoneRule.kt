package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringContainsNoneCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringContainsNoneRule(
    public val chars: Iterable<Char>,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.containsNone(
            chars = chars,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringContainsNoneCheck(chars),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsNoneRule(
    charsAsString: String,
    violationFactory: ViolationFactory<String>,
): StringContainsNoneRule =
    StringContainsNoneRule(
        chars = charsAsString.asIterable(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsNoneRule(charsAsString: String): StringContainsNoneRule =
    StringContainsNoneRule(
        chars = charsAsString.asIterable(),
    )
