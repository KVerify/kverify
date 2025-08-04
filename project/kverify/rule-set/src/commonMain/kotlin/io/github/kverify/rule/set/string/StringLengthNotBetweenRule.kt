package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringLengthNotBetweenCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringLengthNotBetweenRule(
    public val lengthRange: IntRange,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.lengthNotBetween(
            lengthRange = lengthRange,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringLengthNotBetweenCheck(lengthRange),
        violationFactory = violationFactory,
    )
