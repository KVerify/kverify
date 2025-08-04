package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringMaxLengthCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringMaxLengthRule(
    public val maxLength: Int,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.maxLength(
            maxLength = maxLength,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringMaxLengthCheck(maxLength),
        violationFactory = violationFactory,
    )
