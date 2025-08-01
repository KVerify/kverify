package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringLengthNotBetweenCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringLengthNotBetweenRule(
    public val lengthRange: IntRange,
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedLengthNotBetween(
            lengthRange = lengthRange,
        ),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck =
            StringLengthNotBetweenCheck(
                lengthRange = lengthRange,
            ),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringLengthNotBetweenRule(
    minLength: Int,
    maxLength: Int,
    violationFactory: NamedViolationFactory<String>,
): NamedStringLengthNotBetweenRule =
    NamedStringLengthNotBetweenRule(
        lengthRange = minLength..maxLength,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringLengthNotBetweenRule(
    minLength: Int,
    maxLength: Int,
): NamedStringLengthNotBetweenRule =
    NamedStringLengthNotBetweenRule(
        lengthRange = minLength..maxLength,
    )
