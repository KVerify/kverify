package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.StringEndsWithCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

public class StringEndsWithRule(
    public val suffix: String,
    public val ignoreCase: Boolean = false,
    public val violationFactory: ViolationFactory<String> =
        StringViolationFactoryProvider.Default.endsWith(
            suffix = suffix,
            ignoreCase = ignoreCase,
        ),
) : Rule<String> by PredicateRule(
        validationCheck = StringEndsWithCheck(suffix, ignoreCase),
        violationFactory = violationFactory,
    )
