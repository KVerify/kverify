package io.github.kverify.violation.set.provider

import io.github.kverify.core.violation.Violation

@Suppress("TooManyFunctions")
public interface CollectionViolationProvider {
    public fun <E, C : Collection<E>> containsAll(
        value: C,
        elements: Collection<E>,
        name: String? = null,
    ): Violation

    public fun <E, C : Collection<E>> containsNone(
        value: C,
        elements: Collection<E>,
        name: String? = null,
    ): Violation

    public fun <E, C : Collection<E>> containsOnly(
        value: C,
        elements: Collection<E>,
        name: String? = null,
    ): Violation

    public fun <E, C : Collection<E>> contains(
        value: C,
        element: E,
        name: String? = null,
    ): Violation

    public fun <C : Collection<*>> distinct(
        value: C,
        name: String? = null,
    ): Violation

    public fun <C : Collection<*>> maxSize(
        value: C,
        maxSize: Int,
        name: String? = null,
    ): Violation

    public fun <C : Collection<*>> minSize(
        value: C,
        minSize: Int,
        name: String? = null,
    ): Violation

    public fun <E, C : Collection<E>> notContains(
        value: C,
        element: E,
        name: String? = null,
    ): Violation

    public fun <C : Collection<*>> notEmpty(
        value: C,
        name: String? = null,
    ): Violation

    public fun <C : Collection<*>> notOfSize(
        value: C,
        size: Int,
        name: String? = null,
    ): Violation

    public fun <C : Collection<*>> ofSize(
        value: C,
        size: Int,
        name: String? = null,
    ): Violation

    public fun <C : Collection<*>> sizeBetween(
        value: C,
        sizeRange: IntRange,
        name: String? = null,
    ): Violation

    public fun <C : Collection<*>> sizeNotBetween(
        value: C,
        sizeRange: IntRange,
        name: String? = null,
    ): Violation

    public companion object {
        public val Default: CollectionViolationProvider = DefaultCollectionViolationProvider()
    }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationProvider.containsAll(
    value: C,
    element: E,
    name: String? = null,
): Violation =
    containsAll(
        value = value,
        elements = listOf(element),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationProvider.containsAll(
    value: C,
    vararg elements: E,
    name: String? = null,
): Violation =
    containsAll(
        value = value,
        elements = elements.asList(),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationProvider.containsNone(
    value: C,
    element: E,
    name: String? = null,
): Violation =
    containsNone(
        value = value,
        elements = listOf(element),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationProvider.containsNone(
    value: C,
    vararg elements: E,
    name: String? = null,
): Violation =
    containsNone(
        value = value,
        elements = elements.asList(),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationProvider.containsOnly(
    value: C,
    element: E,
    name: String? = null,
): Violation =
    containsOnly(
        value = value,
        elements = listOf(element),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationProvider.containsOnly(
    value: C,
    vararg elements: E,
    name: String? = null,
): Violation =
    containsOnly(
        value = value,
        elements = elements.asList(),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionViolationProvider.sizeBetween(
    value: C,
    minSize: Int,
    maxSize: Int,
    name: String? = null,
): Violation =
    sizeBetween(
        value = value,
        sizeRange = minSize..maxSize,
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionViolationProvider.sizeNotBetween(
    value: C,
    minSize: Int,
    maxSize: Int,
    name: String? = null,
): Violation =
    sizeNotBetween(
        value = value,
        sizeRange = minSize..maxSize,
        name = name,
    )
