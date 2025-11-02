package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringStartsWithCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringStartsWithRule(
    public val prefix: String,
    public val ignoreCase: Boolean = false,
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedStartsWith(
            prefix = prefix,
            ignoreCase = ignoreCase,
        ),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck =
            StringStartsWithCheck(
                prefix = prefix,
                ignoreCase = ignoreCase,
            ),
        violationFactory = violationFactory,
    )
