package io.github.kverify.rule.set.provider

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider
import io.github.kverify.violation.factory.provider.containsAll
import io.github.kverify.violation.factory.provider.containsNone
import io.github.kverify.violation.factory.provider.containsOnly

@Suppress("TooManyFunctions")
public interface CollectionRuleProvider {
    public val collectionViolationFactoryProvider: CollectionViolationFactoryProvider
        get() = CollectionViolationFactoryProvider.Default

    public fun <E, C : Collection<E>> containsAll(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.containsAll(elements),
    ): Rule<C>

    public fun <E, C : Collection<E>> containsNone(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.containsNone(elements),
    ): Rule<C>

    public fun <E, C : Collection<E>> containsOnly(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.containsOnly(elements),
    ): Rule<C>

    public fun <E, C : Collection<E>> contains(
        element: E,
        violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.contains(element),
    ): Rule<C>

    public fun <C : Collection<*>> distinct(violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.distinct()): Rule<C>

    public fun <C : Collection<*>> maxSize(
        maxSize: Int,
        violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.maxSize(maxSize),
    ): Rule<C>

    public fun <C : Collection<*>> minSize(
        minSize: Int,
        violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.minSize(minSize),
    ): Rule<C>

    public fun <E, C : Collection<E>> notContains(
        element: E,
        violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.notContains(element),
    ): Rule<C>

    public fun <C : Collection<*>> notEmpty(violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.notEmpty()): Rule<C>

    public fun <C : Collection<*>> notOfSize(
        size: Int,
        violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.notOfSize(size),
    ): Rule<C>

    public fun <C : Collection<*>> ofSize(
        size: Int,
        violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.ofSize(size),
    ): Rule<C>

    public fun <C : Collection<*>> sizeBetween(
        sizeRange: IntRange,
        violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.sizeBetween(sizeRange),
    ): Rule<C>

    public fun <C : Collection<*>> sizeNotBetween(
        sizeRange: IntRange,
        violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.sizeNotBetween(sizeRange),
    ): Rule<C>
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsAll(
    element: E,
    violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.containsAll(element),
): Rule<C> =
    containsAll(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsAll(
    vararg elements: E,
    violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.containsAll(elements = elements),
): Rule<C> =
    containsAll(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsNone(
    element: E,
    violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.containsNone(element),
): Rule<C> =
    containsNone(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsNone(
    vararg elements: E,
    violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.containsNone(elements = elements),
): Rule<C> =
    containsNone(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsOnly(
    element: E,
    violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.containsOnly(element),
): Rule<C> =
    containsOnly(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsOnly(
    vararg elements: E,
    violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.containsOnly(elements = elements),
): Rule<C> =
    containsOnly(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionRuleProvider.sizeBetween(
    minSize: Int,
    maxSize: Int,
    violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.sizeBetween(minSize..maxSize),
): Rule<C> =
    sizeBetween(
        sizeRange = minSize..maxSize,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionRuleProvider.sizeNotBetween(
    minSize: Int,
    maxSize: Int,
    violationFactory: ViolationFactory<C> = collectionViolationFactoryProvider.sizeNotBetween(minSize..maxSize),
): Rule<C> =
    sizeNotBetween(
        sizeRange = minSize..maxSize,
        violationFactory = violationFactory,
    )
