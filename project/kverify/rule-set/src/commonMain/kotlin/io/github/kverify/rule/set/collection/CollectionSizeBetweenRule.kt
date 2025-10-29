package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionSizeBetweenCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionSizeBetweenRule<C : Collection<*>>(
    public val sizeRange: IntRange,
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.sizeBetween(
            sizeRange = sizeRange,
        ),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionSizeBetweenCheck(sizeRange),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionSizeBetweenRule(
    minSize: Int,
    maxSize: Int,
): CollectionSizeBetweenRule<C> =
    CollectionSizeBetweenRule(
        sizeRange = minSize..maxSize,
    )
