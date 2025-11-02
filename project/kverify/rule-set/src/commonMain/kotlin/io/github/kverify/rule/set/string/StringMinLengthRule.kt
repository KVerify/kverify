package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringMinLengthCheck
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringMinLengthRule(
    public val minLength: Int,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.minLength(
            minLength = minLength,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringMinLengthCheck(minLength),
        violationFactory = violationFactory,
    )
