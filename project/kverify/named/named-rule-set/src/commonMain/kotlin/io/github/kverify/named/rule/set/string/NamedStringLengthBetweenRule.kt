package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringLengthBetweenCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringLengthBetweenRule(
    public val lengthRange: IntRange,
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedLengthBetween(
            lengthRange = lengthRange,
        ),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck =
            StringLengthBetweenCheck(
                lengthRange = lengthRange,
            ),
        violationFactory = violationFactory,
    )
