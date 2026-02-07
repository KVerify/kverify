package io.github.kverify.rule.set.string

import io.github.kverify.check.set.string.NotBlankCheck
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.set.string.NotBlankViolationFactory

public class NotBlankRule(
    violationFactory: ViolationFactory<String> = NotBlankViolationFactory(),
) : PredicateRule<String>(
        validationCheck = NotBlankCheck,
        violationFactory = violationFactory,
    ) {
    public companion object : Rule<String> by NotBlankRule()
}

@Suppress("NOTHING_TO_INLINE")
public inline fun NotBlankRule(message: String): NotBlankRule =
    NotBlankRule(
        violationFactory =
            NotBlankViolationFactory(
                reason = message,
            ),
    )
