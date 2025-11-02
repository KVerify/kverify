package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringStartsWithCheck
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringStartsWithRule(
    public val prefix: String,
    public val ignoreCase: Boolean = false,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.startsWith(
            prefix = prefix,
            ignoreCase = ignoreCase,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringStartsWithCheck(prefix, ignoreCase),
        violationFactory = violationFactory,
    )
