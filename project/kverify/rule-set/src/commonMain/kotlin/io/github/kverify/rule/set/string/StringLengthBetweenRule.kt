package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringLengthBetweenCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public open class StringLengthBetweenRule(
    public val lengthRange: IntRange,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.lengthBetween(
            lengthRange = lengthRange,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringLengthBetweenCheck(lengthRange),
        violationFactory = violationFactory,
    )
