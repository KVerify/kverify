package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.violation.set.provider.CollectionViolationProvider

@Suppress("TooManyFunctions")
public interface NamedCollectionRuleProvider {
    public val collectionViolationProvider: CollectionViolationProvider
        get() = CollectionViolationProvider.Default

    public fun <E, C : Collection<E>> namedContainsAll(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C> =
            NamedViolationFactory { (name, value) ->
                collectionViolationProvider.containsAll(
                    value = value,
                    elements = elements,
                    name = name,
                )
            },
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedContainsNone(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C> =
            NamedViolationFactory { (name, value) ->
                collectionViolationProvider.containsNone(
                    value = value,
                    elements = elements,
                    name = name,
                )
            },
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedContainsOnly(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C> =
            NamedViolationFactory { (name, value) ->
                collectionViolationProvider.containsOnly(
                    value = value,
                    elements = elements,
                    name = name,
                )
            },
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedContains(
        element: E,
        violationFactory: NamedViolationFactory<C> =
            NamedViolationFactory { (name, value) ->
                collectionViolationProvider.contains(
                    value = value,
                    element = element,
                    name = name,
                )
            },
    ): NamedRule<C>

    public fun <C : Collection<*>> namedDistinct(
        violationFactory: NamedViolationFactory<C> =
            NamedViolationFactory { (name, value) ->
                collectionViolationProvider.distinct(
                    value = value,
                    name = name,
                )
            },
    ): NamedRule<C>

    public fun <C : Collection<*>> namedMaxSize(
        maxSize: Int,
        violationFactory: NamedViolationFactory<C> =
            NamedViolationFactory { (name, value) ->
                collectionViolationProvider.maxSize(
                    value = value,
                    maxSize = maxSize,
                    name = name,
                )
            },
    ): NamedRule<C>

    public fun <C : Collection<*>> namedMinSize(
        minSize: Int,
        violationFactory: NamedViolationFactory<C> =
            NamedViolationFactory { (name, value) ->
                collectionViolationProvider.minSize(
                    value = value,
                    minSize = minSize,
                    name = name,
                )
            },
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedNotContains(
        element: E,
        violationFactory: NamedViolationFactory<C> =
            NamedViolationFactory { (name, value) ->
                collectionViolationProvider.notContains(
                    value = value,
                    element = element,
                    name = name,
                )
            },
    ): NamedRule<C>

    public fun <C : Collection<*>> namedNotEmpty(
        violationFactory: NamedViolationFactory<C> =
            NamedViolationFactory { (name, value) ->
                collectionViolationProvider.notEmpty(
                    value = value,
                    name = name,
                )
            },
    ): NamedRule<C>

    public fun <C : Collection<*>> namedNotOfSize(
        size: Int,
        violationFactory: NamedViolationFactory<C> =
            NamedViolationFactory { (name, value) ->
                collectionViolationProvider.notOfSize(
                    value = value,
                    size = size,
                    name = name,
                )
            },
    ): NamedRule<C>

    public fun <C : Collection<*>> namedOfSize(
        size: Int,
        violationFactory: NamedViolationFactory<C> =
            NamedViolationFactory { (name, value) ->
                collectionViolationProvider.ofSize(
                    value = value,
                    size = size,
                    name = name,
                )
            },
    ): NamedRule<C>

    public fun <C : Collection<*>> namedSizeBetween(
        sizeRange: IntRange,
        violationFactory: NamedViolationFactory<C> =
            NamedViolationFactory { (name, value) ->
                collectionViolationProvider.sizeBetween(
                    value = value,
                    sizeRange = sizeRange,
                    name = name,
                )
            },
    ): NamedRule<C>

    public fun <C : Collection<*>> namedSizeNotBetween(
        sizeRange: IntRange,
        violationFactory: NamedViolationFactory<C> =
            NamedViolationFactory { (name, value) ->
                collectionViolationProvider.sizeNotBetween(
                    value = value,
                    sizeRange = sizeRange,
                    name = name,
                )
            },
    ): NamedRule<C>
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsAll(
    element: E,
    violationFactory: NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.containsAll(
                value = value,
                elements = listOf(element),
                name = name,
            )
        },
): NamedRule<C> =

    namedContainsAll(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsAll(
    vararg elements: E,
    violationFactory: NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.containsAll(
                value = value,
                elements = elements.asList(),
                name = name,
            )
        },
): NamedRule<C> =
    namedContainsAll(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsNone(
    element: E,
    violationFactory: NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.containsNone(
                value = value,
                elements = listOf(element),
                name = name,
            )
        },
): NamedRule<C> =
    namedContainsNone(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsNone(
    vararg elements: E,
    violationFactory: NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.containsNone(
                value = value,
                elements = elements.asList(),
                name = name,
            )
        },
): NamedRule<C> =
    namedContainsNone(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsOnly(
    element: E,
    violationFactory: NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.containsOnly(
                value = value,
                elements = listOf(element),
                name = name,
            )
        },
): NamedRule<C> =
    namedContainsOnly(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> NamedCollectionRuleProvider.namedContainsOnly(
    vararg elements: E,
    violationFactory: NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.containsOnly(
                value = value,
                elements = elements.asList(),
                name = name,
            )
        },
): NamedRule<C> =
    namedContainsOnly(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionRuleProvider.namedSizeBetween(
    minSize: Int,
    maxSize: Int,
    violationFactory: NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.sizeBetween(
                value = value,
                sizeRange = minSize..maxSize,
                name = name,
            )
        },
): NamedRule<C> =
    namedSizeBetween(
        sizeRange = minSize..maxSize,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> NamedCollectionRuleProvider.namedSizeNotBetween(
    minSize: Int,
    maxSize: Int,
    violationFactory: NamedViolationFactory<C> =
        NamedViolationFactory { (name, value) ->
            collectionViolationProvider.sizeNotBetween(
                value = value,
                sizeRange = minSize..maxSize,
                name = name,
            )
        },
): NamedRule<C> =
    namedSizeNotBetween(
        sizeRange = minSize..maxSize,
        violationFactory = violationFactory,
    )
