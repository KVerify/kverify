package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionSizeNotBetweenCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionSizeNotBetweenRule<C : Collection<*>>(
    public val sizeRange: IntRange,
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.sizeNotBetween(
            sizeRange = sizeRange,
        ),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionSizeNotBetweenCheck(sizeRange),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionSizeNotBetweenRule(
    minSize: Int,
    maxSize: Int,
): CollectionSizeNotBetweenRule<C> =
    CollectionSizeNotBetweenRule(
        sizeRange = minSize..maxSize,
    )
