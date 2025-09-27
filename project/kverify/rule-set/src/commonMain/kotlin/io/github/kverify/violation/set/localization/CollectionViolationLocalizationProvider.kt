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
