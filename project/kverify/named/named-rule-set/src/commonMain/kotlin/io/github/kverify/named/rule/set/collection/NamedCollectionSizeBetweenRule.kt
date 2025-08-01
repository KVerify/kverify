package io.github.kverify.named.rule.set.collection

import io.github.kverify.check.set.collection.CollectionSizeBetweenCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider

public class NamedCollectionSizeBetweenRule<C : Collection<*>>(
    public val sizeRange: IntRange,
    public val violationFactory: NamedViolationFactory<C> =
        NamedCollectionViolationFactoryProvider.Default.namedSizeBetween(
            sizeRange = sizeRange,
        ),
) : NamedRule<C> by NamedPredicateRule(
        validationCheck = CollectionSizeBetweenCheck(sizeRange),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionSizeBetweenRule(
    minSize: Int,
    maxSize: Int,
    violationFactory: NamedViolationFactory<C>,
): NamedCollectionSizeBetweenRule<C> =
    NamedCollectionSizeBetweenRule(
        sizeRange = minSize..maxSize,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionSizeBetweenRule(
    minSize: Int,
    maxSize: Int,
): NamedCollectionSizeBetweenRule<C> =
    NamedCollectionSizeBetweenRule(
        sizeRange = minSize..maxSize,
    )
