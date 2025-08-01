package io.github.kverify.named.rule.set.collection

import io.github.kverify.check.set.collection.CollectionSizeNotBetweenCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider

public class NamedCollectionSizeNotBetweenRule<C : Collection<*>>(
    public val sizeRange: IntRange,
    public val violationFactory: NamedViolationFactory<C> =
        NamedCollectionViolationFactoryProvider.Default.namedSizeNotBetween(
            sizeRange = sizeRange,
        ),
) : NamedRule<C> by NamedPredicateRule(
        validationCheck = CollectionSizeNotBetweenCheck(sizeRange),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionSizeNotBetweenRule(
    minSize: Int,
    maxSize: Int,
    violationFactory: NamedViolationFactory<C>,
): NamedCollectionSizeNotBetweenRule<C> =
    NamedCollectionSizeNotBetweenRule(
        sizeRange = minSize..maxSize,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionSizeNotBetweenRule(
    minSize: Int,
    maxSize: Int,
): NamedCollectionSizeNotBetweenRule<C> =
    NamedCollectionSizeNotBetweenRule(
        sizeRange = minSize..maxSize,
    )
