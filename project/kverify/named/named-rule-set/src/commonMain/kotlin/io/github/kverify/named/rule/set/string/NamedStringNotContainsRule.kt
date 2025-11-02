package io.github.kverify.named.rule.set.string

import io.github.kverify.check.set.string.StringContainsCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

public class NamedStringNotContainsRule(
    public val substring: String,
    public val ignoreCase: Boolean = false,
    public val violationFactory: NamedViolationFactory<String> =
        NamedStringViolationFactoryProvider.Default.namedNotContains(
            substring = substring,
            ignoreCase = ignoreCase,
        ),
) : NamedRule<String> by NamedPredicateRule(
        validationCheck =
            StringContainsCheck(
                substring = substring,
                ignoreCase = ignoreCase,
            ),
        violationFactory = violationFactory,
    )
