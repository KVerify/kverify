package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringNotOfLengthCheck
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringNotOfLengthRule(
    public val length: Int,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.notOfLength(length),
) : Rule<String> by PredicateRule(
        validationCheck = StringNotOfLengthCheck(length),
        violationFactory = violationFactory,
    )
