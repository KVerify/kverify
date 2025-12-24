package io.github.kverify.rule.set.provider

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.set.provider.CollectionViolationProvider

@Suppress("TooManyFunctions")
public interface CollectionRuleProvider {
    public val collectionViolationProvider: CollectionViolationProvider
        get() = CollectionViolationProvider.Default

    public fun <E, C : Collection<E>> containsAll(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C> =
            ViolationFactory { value ->
                collectionViolationProvider.containsAll(
                    value = value,
                    elements = elements,
                )
            },
    ): Rule<C>

    public fun <E, C : Collection<E>> containsNone(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C> =
            ViolationFactory { value ->
                collectionViolationProvider.containsNone(
                    value = value,
                    elements = elements,
                )
            },
    ): Rule<C>

    public fun <E, C : Collection<E>> containsOnly(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C> =
            ViolationFactory { value ->
                collectionViolationProvider.containsOnly(
                    value = value,
                    elements = elements,
                )
            },
    ): Rule<C>

    public fun <E, C : Collection<E>> contains(
        element: E,
        violationFactory: ViolationFactory<C> =
            ViolationFactory { value ->
                collectionViolationProvider.contains(
                    value = value,
                    element = element,
                )
            },
    ): Rule<C>

    public fun <C : Collection<*>> distinct(
        violationFactory: ViolationFactory<C> =
            ViolationFactory { value ->
                collectionViolationProvider.distinct(
                    value = value,
                )
            },
    ): Rule<C>

    public fun <C : Collection<*>> maxSize(
        maxSize: Int,
        violationFactory: ViolationFactory<C> =
            ViolationFactory { value ->
                collectionViolationProvider.maxSize(
                    value = value,
                    maxSize = maxSize,
                )
            },
    ): Rule<C>

    public fun <C : Collection<*>> minSize(
        minSize: Int,
        violationFactory: ViolationFactory<C> =
            ViolationFactory { value ->
                collectionViolationProvider.minSize(
                    value = value,
                    minSize = minSize,
                )
            },
    ): Rule<C>

    public fun <E, C : Collection<E>> notContains(
        element: E,
        violationFactory: ViolationFactory<C> =
            ViolationFactory { value ->
                collectionViolationProvider.notContains(
                    value = value,
                    element = element,
                )
            },
    ): Rule<C>

    public fun <C : Collection<*>> notEmpty(
        violationFactory: ViolationFactory<C> =
            ViolationFactory { value ->
                collectionViolationProvider.notEmpty(
                    value = value,
                )
            },
    ): Rule<C>

    public fun <C : Collection<*>> notOfSize(
        size: Int,
        violationFactory: ViolationFactory<C> =
            ViolationFactory { value ->
                collectionViolationProvider.notOfSize(
                    value = value,
                    size = size,
                )
            },
    ): Rule<C>

    public fun <C : Collection<*>> ofSize(
        size: Int,
        violationFactory: ViolationFactory<C> =
            ViolationFactory { value ->
                collectionViolationProvider.ofSize(
                    value = value,
                    size = size,
                )
            },
    ): Rule<C>

    public fun <C : Collection<*>> sizeBetween(
        sizeRange: IntRange,
        violationFactory: ViolationFactory<C> =
            ViolationFactory { value ->
                collectionViolationProvider.sizeBetween(
                    value = value,
                    sizeRange = sizeRange,
                )
            },
    ): Rule<C>

    public fun <C : Collection<*>> sizeNotBetween(
        sizeRange: IntRange,
        violationFactory: ViolationFactory<C> =
            ViolationFactory { value ->
                collectionViolationProvider.sizeNotBetween(
                    value = value,
                    sizeRange = sizeRange,
                )
            },
    ): Rule<C>
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsAll(
    element: E,
    violationFactory: ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.containsAll(
                value = value,
                elements = listOf(element),
            )
        },
): Rule<C> =
    containsAll(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsAll(
    vararg elements: E,
    violationFactory: ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.containsAll(
                value = value,
                elements = elements.asList(),
            )
        },
): Rule<C> =
    containsAll(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsNone(
    element: E,
    violationFactory: ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.containsNone(
                value = value,
                elements = listOf(element),
            )
        },
): Rule<C> =
    containsNone(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsNone(
    vararg elements: E,
    violationFactory: ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.containsNone(
                value = value,
                elements = elements.asList(),
            )
        },
): Rule<C> =
    containsNone(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsOnly(
    element: E,
    violationFactory: ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.containsOnly(
                value = value,
                elements = listOf(element),
            )
        },
): Rule<C> =
    containsOnly(
        elements = listOf(element),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionRuleProvider.containsOnly(
    vararg elements: E,
    violationFactory: ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.containsOnly(
                value = value,
                elements = elements.asList(),
            )
        },
): Rule<C> =
    containsOnly(
        elements = elements.asList(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionRuleProvider.sizeBetween(
    minSize: Int,
    maxSize: Int,
    violationFactory: ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.sizeBetween(
                value = value,
                sizeRange = minSize..maxSize,
            )
        },
): Rule<C> =
    sizeBetween(
        sizeRange = minSize..maxSize,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionRuleProvider.sizeNotBetween(
    minSize: Int,
    maxSize: Int,
    violationFactory: ViolationFactory<C> =
        ViolationFactory { value ->
            collectionViolationProvider.sizeNotBetween(
                value = value,
                sizeRange = minSize..maxSize,
            )
        },
): Rule<C> =
    sizeNotBetween(
        sizeRange = minSize..maxSize,
        violationFactory = violationFactory,
    )
