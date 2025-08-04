package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringOfLengthCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringOfLengthRule(
    public val length: Int,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.ofLength(length),
) : Rule<String> by PredicateRule(
        validationCheck = StringOfLengthCheck(length),
        violationFactory = violationFactory,
    )
