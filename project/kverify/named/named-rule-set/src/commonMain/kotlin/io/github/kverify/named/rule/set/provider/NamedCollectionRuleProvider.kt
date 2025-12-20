package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedCollectionViolationFactoryProvider
import io.github.kverify.named.violation.factory.provider.namedContainsAll
import io.github.kverify.named.violation.factory.provider.namedContainsNone
import io.github.kverify.named.violation.factory.provider.namedContainsOnly

@Suppress("TooManyFunctions")
public interface NamedCollectionRuleProvider {
    public val namedCollectionViolationFactoryProvider: NamedCollectionViolationFactoryProvider
        get() = NamedCollectionViolationFactoryProvider.Default

    public fun <E, C : Collection<E>> namedContainsAll(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedContainsAll(elements),
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedContainsNone(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedContainsNone(elements),
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedContainsOnly(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedContainsOnly(elements),
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedContains(
        element: E,
        violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedContains(element),
    ): NamedRule<C>

    public fun <C : Collection<*>> namedDistinct(
        violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedDistinct(),
    ): NamedRule<C>

    public fun <C : Collection<*>> namedMaxSize(
        maxSize: Int,
        violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedMaxSize(maxSize),
    ): NamedRule<C>

    public fun <C : Collection<*>> namedMinSize(
        minSize: Int,
        violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedMinSize(minSize),
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedNotContains(
        element: E,
        violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedNotContains(element),
    ): NamedRule<C>

    public fun <C : Collection<*>> namedNotEmpty(
        violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedNotEmpty(),
    ): NamedRule<C>

    public fun <C : Collection<*>> namedNotOfSize(
        size: Int,
        violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedNotOfSize(size),
    ): NamedRule<C>

    public fun <C : Collection<*>> namedOfSize(
        size: Int,
        violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedOfSize(size),
    ): NamedRule<C>

    public fun <C : Collection<*>> namedSizeBetween(
        sizeRange: IntRange,
        violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedSizeBetween(sizeRange),
    ): NamedRule<C>

    public fun <C : Collection<*>> namedSizeNotBetween(
        sizeRange: IntRange,
        violationFactory: NamedViolationFactory<C> =
            namedCollectionViolationFactoryProvider.namedSizeNotBetween(
                sizeRange,
            ),
    ): NamedRule<C>
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsAll(
    element: E,
    violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedContainsAll(element),
): NamedRule<C> =

    namedContainsAll(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsAll(
    vararg elements: E,
    violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedContainsAll(elements = elements),
): NamedRule<C> =
    namedContainsAll(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsNone(
    element: E,
    violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedContainsNone(element),
): NamedRule<C> =
    namedContainsNone(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsNone(
    vararg elements: E,
    violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedContainsNone(elements = elements),
): NamedRule<C> =
    namedContainsNone(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsOnly(
    element: E,
    violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedContainsOnly(element),
): NamedRule<C> =
    namedContainsOnly(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsOnly(
    vararg elements: E,
    violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedContainsOnly(elements = elements),
): NamedRule<C> =
    namedContainsOnly(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionRuleProvider.namedSizeBetween(
    minSize: Int,
    maxSize: Int,
    violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedSizeBetween(minSize..maxSize),
): NamedRule<C> =
    namedSizeBetween(
        sizeRange = minSize..maxSize,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionRuleProvider.namedSizeNotBetween(
    minSize: Int,
    maxSize: Int,
    violationFactory: NamedViolationFactory<C> = namedCollectionViolationFactoryProvider.namedSizeNotBetween(minSize..maxSize),
): NamedRule<C> =
    namedSizeNotBetween(
        sizeRange = minSize..maxSize,
        violationFactory = violationFactory,
    )
