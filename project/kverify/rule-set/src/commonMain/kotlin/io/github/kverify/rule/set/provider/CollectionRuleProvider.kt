package io.github.kverify.rule.set.provider

import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.rule.Rule

@Suppress("TooManyFunctions")
public interface CollectionRuleProvider {
    public fun <E, C : Collection<E>> containsAll(elements: Collection<E>): Rule<C>

    public fun <E, C : Collection<E>> containsNone(elements: Collection<E>): Rule<C>

    public fun <E, C : Collection<E>> containsOnly(elements: Collection<E>): Rule<C>

    public fun <E, C : Collection<E>> contains(element: E): Rule<C>

    public fun <C : Collection<*>> distinct(): Rule<C>

    public fun <C : Collection<*>> maxSize(maxSize: Int): Rule<C>

    public fun <C : Collection<*>> minSize(minSize: Int): Rule<C>

    public fun <E, C : Collection<E>> notContains(element: E): Rule<C>

    public fun <C : Collection<*>> notEmpty(): Rule<C>

    public fun <C : Collection<*>> notOfSize(size: Int): Rule<C>

    public fun <C : Collection<*>> ofSize(size: Int): Rule<C>

    public fun <C : Collection<*>> sizeBetween(sizeRange: IntRange): Rule<C>

    public fun <C : Collection<*>> sizeNotBetween(sizeRange: IntRange): Rule<C>
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsAll(element: E): Rule<C> =
    containsAll(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsAll(vararg elements: E): Rule<C> =
    containsAll(
        elements = elements.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsNone(element: E): Rule<C> =
    containsNone(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsNone(vararg elements: E): Rule<C> =
    containsNone(
        elements = elements.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsOnly(element: E): Rule<C> =
    containsOnly(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsOnly(vararg elements: E): Rule<C> =
    containsOnly(
        elements = elements.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionRuleProvider.sizeBetween(
    minSize: Int,
    maxSize: Int,
): Rule<C> =
    sizeBetween(
        sizeRange = minSize..maxSize,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionRuleProvider.sizeNotBetween(
    minSize: Int,
    maxSize: Int,
): Rule<C> =
    sizeNotBetween(
        sizeRange = minSize..maxSize,
    )

@Suppress("TooManyFunctions")
public interface CollectionRuleWithFactoryProvider {
    public fun <E, C : Collection<E>> containsAll(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <E, C : Collection<E>> containsNone(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <E, C : Collection<E>> containsOnly(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <E, C : Collection<E>> contains(
        element: E,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <C : Collection<*>> distinct(violationFactory: ViolationFactory<C>): Rule<C>

    public fun <C : Collection<*>> maxSize(
        maxSize: Int,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <C : Collection<*>> minSize(
        minSize: Int,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <E, C : Collection<E>> notContains(
        element: E,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <C : Collection<*>> notEmpty(violationFactory: ViolationFactory<C>): Rule<C>

    public fun <C : Collection<*>> notOfSize(
        size: Int,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <C : Collection<*>> ofSize(
        size: Int,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <C : Collection<*>> sizeBetween(
        sizeRange: IntRange,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <C : Collection<*>> sizeNotBetween(
        sizeRange: IntRange,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleWithFactoryProvider.containsAll(
    element: E,
    violationFactory: ViolationFactory<C>,
): Rule<C> =
    containsAll(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleWithFactoryProvider.containsAll(
    vararg elements: E,
    violationFactory: ViolationFactory<C>,
): Rule<C> =
    containsAll(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleWithFactoryProvider.containsNone(
    element: E,
    violationFactory: ViolationFactory<C>,
): Rule<C> =
    containsNone(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleWithFactoryProvider.containsNone(
    vararg elements: E,
    violationFactory: ViolationFactory<C>,
): Rule<C> =
    containsNone(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleWithFactoryProvider.containsOnly(
    element: E,
    violationFactory: ViolationFactory<C>,
): Rule<C> =
    containsOnly(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleWithFactoryProvider.containsOnly(
    vararg elements: E,
    violationFactory: ViolationFactory<C>,
): Rule<C> =
    containsOnly(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionRuleWithFactoryProvider.sizeBetween(
    minSize: Int,
    maxSize: Int,
    violationFactory: ViolationFactory<C>,
): Rule<C> =
    sizeBetween(
        sizeRange = minSize..maxSize,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionRuleWithFactoryProvider.sizeNotBetween(
    minSize: Int,
    maxSize: Int,
    violationFactory: ViolationFactory<C>,
): Rule<C> =
    sizeNotBetween(
        sizeRange = minSize..maxSize,
        violationFactory = violationFactory,
    )
