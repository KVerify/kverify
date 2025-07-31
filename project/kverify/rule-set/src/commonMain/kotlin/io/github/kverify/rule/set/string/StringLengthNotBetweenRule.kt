package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringLengthNotBetweenCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public open class StringLengthNotBetweenRule(
    public val range: IntRange,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.lengthNotBetween(
            range = range,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringLengthNotBetweenCheck(range),
        violationFactory = violationFactory,
    )
