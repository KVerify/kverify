package io.github.kverify.violation.set.localization

@Suppress("TooManyFunctions")
public interface CollectionViolationLocalizationProvider {
    public fun <E, C : Collection<E>> containsAll(
        value: C,
        elements: Collection<E>,
        name: String? = null,
    ): String

    public fun <E, C : Collection<E>> containsNone(
        value: C,
        elements: Collection<E>,
        name: String? = null,
    ): String

    public fun <E, C : Collection<E>> containsOnly(
        value: C,
        elements: Collection<E>,
        name: String? = null,
    ): String

    public fun <E, C : Collection<E>> contains(
        value: C,
        element: E,
        name: String? = null,
    ): String

    public fun <C : Collection<*>> distinct(
        value: C,
        name: String? = null,
    ): String

    public fun <C : Collection<*>> maxSize(
        value: C,
        maxSize: Int,
        name: String? = null,
    ): String

    public fun <C : Collection<*>> minSize(
        value: C,
        minSize: Int,
        name: String? = null,
    ): String

    public fun <E, C : Collection<E>> notContains(
        value: C,
        element: E,
        name: String? = null,
    ): String

    public fun <C : Collection<*>> notEmpty(
        value: C,
        name: String? = null,
    ): String

    public fun <C : Collection<*>> notOfSize(
        value: C,
        size: Int,
        name: String? = null,
    ): String

    public fun <C : Collection<*>> ofSize(
        value: C,
        size: Int,
        name: String? = null,
    ): String

    public fun <C : Collection<*>> sizeBetween(
        value: C,
        sizeRange: IntRange,
        name: String? = null,
    ): String

    public fun <C : Collection<*>> sizeNotBetween(
        value: C,
        sizeRange: IntRange,
        name: String? = null,
    ): String

    public companion object {
        public val Default: CollectionViolationLocalizationProvider = DefaultCollectionViolationLocalizationProvider
    }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationLocalizationProvider.containsAll(
    value: C,
    element: E,
    name: String? = null,
): String =
    containsAll(
        value = value,
        elements = listOf(element),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationLocalizationProvider.containsAll(
    value: C,
    vararg elements: E,
    name: String? = null,
): String =
    containsAll(
        value = value,
        elements = elements.asList(),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationLocalizationProvider.containsNone(
    value: C,
    element: E,
    name: String? = null,
): String =
    containsNone(
        value = value,
        elements = listOf(element),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationLocalizationProvider.containsNone(
    value: C,
    vararg elements: E,
    name: String? = null,
): String =
    containsNone(
        value = value,
        elements = elements.asList(),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationLocalizationProvider.containsOnly(
    value: C,
    element: E,
    name: String? = null,
): String =
    containsOnly(
        value = value,
        elements = listOf(element),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionViolationLocalizationProvider.containsOnly(
    value: C,
    vararg elements: E,
    name: String? = null,
): String =
    containsOnly(
        value = value,
        elements = elements.asList(),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionViolationLocalizationProvider.sizeBetween(
    value: C,
    minSize: Int,
    maxSize: Int,
    name: String? = null,
): String =
    sizeBetween(
        value = value,
        sizeRange = minSize..maxSize,
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionViolationLocalizationProvider.sizeNotBetween(
    value: C,
    minSize: Int,
    maxSize: Int,
    name: String? = null,
): String =
    sizeNotBetween(
        value = value,
        sizeRange = minSize..maxSize,
        name = name,
    )
