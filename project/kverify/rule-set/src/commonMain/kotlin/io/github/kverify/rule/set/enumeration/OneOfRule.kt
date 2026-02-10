package io.github.kverify.rule.set.enumeration

import io.github.kverify.check.set.enumeration.OneOfCheck
import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ViolationFactory
import io.github.kverify.violation.factory.set.enumeration.OneOfViolationFactory

public class OneOfRule<T>(
    public val allowed: Set<T>,
    violationFactory: ViolationFactory<T> = OneOfViolationFactory(allowed),
) : PredicateRule<T>(
        validationCheck = OneOfCheck(allowed),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> OneOfRule(
    allowed: Set<T>,
    reason: String,
): OneOfRule<T> =
    OneOfRule(
        allowed = allowed,
        violationFactory =
            OneOfViolationFactory(
                allowed = allowed,
                reason = reason,
            ),
    )
