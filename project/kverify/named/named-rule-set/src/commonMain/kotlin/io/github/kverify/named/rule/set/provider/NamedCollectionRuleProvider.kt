@file:Suppress("TooManyFunctions")

package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedRule

public interface NamedCollectionRuleProvider {
    public fun <E, C : Collection<E>> namedContainsAll(elements: Collection<E>): NamedRule<C>

    public fun <E, C : Collection<E>> namedContainsNone(elements: Collection<E>): NamedRule<C>

    public fun <E, C : Collection<E>> namedContainsOnly(elements: Collection<E>): NamedRule<C>

    public fun <E, C : Collection<E>> namedContains(element: E): NamedRule<C>

    public fun <C : Collection<*>> namedDistinct(): NamedRule<C>

    public fun <C : Collection<*>> namedMaxSize(maxSize: Int): NamedRule<C>

    public fun <C : Collection<*>> namedMinSize(minSize: Int): NamedRule<C>

    public fun <E, C : Collection<E>> namedNotContains(element: E): NamedRule<C>

    public fun <C : Collection<*>> namedNotEmpty(): NamedRule<C>

    public fun <C : Collection<*>> namedNotOfSize(size: Int): NamedRule<C>

    public fun <C : Collection<*>> namedOfSize(size: Int): NamedRule<C>

    public fun <C : Collection<*>> namedSizeBetween(sizeRange: IntRange): NamedRule<C>

    public fun <C : Collection<*>> namedSizeNotBetween(sizeRange: IntRange): NamedRule<C>
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.containsAll(element: E): NamedRule<C> =
    namedContainsAll(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsAll(vararg elements: E): NamedRule<C> =
    namedContainsAll(
        elements = elements.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsNone(element: E): NamedRule<C> =
    namedContainsNone(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsNone(vararg elements: E): NamedRule<C> =
    namedContainsNone(
        elements = elements.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsOnly(element: E): NamedRule<C> =
    namedContainsOnly(
        elements = listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsOnly(vararg elements: E): NamedRule<C> =
    namedContainsOnly(
        elements = elements.asList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionRuleProvider.namedSizeBetween(
    minSize: Int,
    maxSize: Int,
): NamedRule<C> =
    namedSizeBetween(
        sizeRange = minSize..maxSize,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionRuleProvider.namedSizeNotBetween(
    minSize: Int,
    maxSize: Int,
): NamedRule<C> =
    namedSizeNotBetween(
        sizeRange = minSize..maxSize,
    )

@Suppress("TooManyFunctions")
public interface NamedCollectionRuleWithFactoryProvider {
    public fun <E, C : Collection<E>> namedContainsAll(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedContainsNone(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedContainsOnly(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedContains(
        element: E,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <C : Collection<*>> namedDistinct(violationFactory: NamedViolationFactory<C>): NamedRule<C>

    public fun <C : Collection<*>> namedMaxSize(
        maxSize: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <C : Collection<*>> namedMinSize(
        minSize: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedNotContains(
        element: E,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <C : Collection<*>> namedNotEmpty(violationFactory: NamedViolationFactory<C>): NamedRule<C>

    public fun <C : Collection<*>> namedNotOfSize(
        size: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <C : Collection<*>> namedOfSize(
        size: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <C : Collection<*>> namedSizeBetween(
        sizeRange: IntRange,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <C : Collection<*>> namedSizeNotBetween(
        sizeRange: IntRange,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleWithFactoryProvider.containsAll(
    element: E,
    violationFactory: NamedViolationFactory<C>,
): NamedRule<C> =
    namedContainsAll(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleWithFactoryProvider.namedContainsAll(
    vararg elements: E,
    violationFactory: NamedViolationFactory<C>,
): NamedRule<C> =
    namedContainsAll(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleWithFactoryProvider.namedContainsNone(
    element: E,
    violationFactory: NamedViolationFactory<C>,
): NamedRule<C> =
    namedContainsNone(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleWithFactoryProvider.namedContainsNone(
    vararg elements: E,
    violationFactory: NamedViolationFactory<C>,
): NamedRule<C> =
    namedContainsNone(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleWithFactoryProvider.namedContainsOnly(
    element: E,
    violationFactory: NamedViolationFactory<C>,
): NamedRule<C> =
    namedContainsOnly(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleWithFactoryProvider.namedContainsOnly(
    vararg elements: E,
    violationFactory: NamedViolationFactory<C>,
): NamedRule<C> =
    namedContainsOnly(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionRuleWithFactoryProvider.namedSizeBetween(
    minSize: Int,
    maxSize: Int,
    violationFactory: NamedViolationFactory<C>,
): NamedRule<C> =
    namedSizeBetween(
        sizeRange = minSize..maxSize,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionRuleWithFactoryProvider.namedSizeNotBetween(
    minSize: Int,
    maxSize: Int,
    violationFactory: NamedViolationFactory<C>,
): NamedRule<C> =
    namedSizeNotBetween(
        sizeRange = minSize..maxSize,
        violationFactory = violationFactory,
    )
