package io.github.kverify.violation.set.provider

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolationReason
import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider

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
        public val Default: CollectionViolationProvider = CollectionViolations()
    }
}

@Suppress("TooManyFunctions")
public class CollectionViolations(
    private val collectionViolationLocalizationProvider: CollectionViolationLocalizationProvider =
        CollectionViolationLocalizationProvider.Default,
) : CollectionViolationProvider {
    override fun <E, C : Collection<E>> containsAll(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): Violation =
        collectionViolationLocalizationProvider
            .containsAll(
                value = value,
                elements = elements,
                name = name,
            ).asViolationReason()

    override fun <E, C : Collection<E>> containsNone(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): Violation =
        collectionViolationLocalizationProvider
            .containsNone(
                value = value,
                elements = elements,
                name = name,
            ).asViolationReason()

    override fun <E, C : Collection<E>> containsOnly(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): Violation =
        collectionViolationLocalizationProvider
            .containsOnly(
                value = value,
                elements = elements,
                name = name,
            ).asViolationReason()

    override fun <E, C : Collection<E>> contains(
        value: C,
        element: E,
        name: String?,
    ): Violation =
        collectionViolationLocalizationProvider
            .contains(
                value = value,
                element = element,
                name = name,
            ).asViolationReason()

    override fun <C : Collection<*>> distinct(
        value: C,
        name: String?,
    ): Violation =
        collectionViolationLocalizationProvider
            .distinct(
                value = value,
                name = name,
            ).asViolationReason()

    override fun <C : Collection<*>> maxSize(
        value: C,
        maxSize: Int,
        name: String?,
    ): Violation =
        collectionViolationLocalizationProvider
            .maxSize(
                value = value,
                maxSize = maxSize,
                name = name,
            ).asViolationReason()

    override fun <C : Collection<*>> minSize(
        value: C,
        minSize: Int,
        name: String?,
    ): Violation =
        collectionViolationLocalizationProvider
            .minSize(
                value = value,
                minSize = minSize,
                name = name,
            ).asViolationReason()

    override fun <E, C : Collection<E>> notContains(
        value: C,
        element: E,
        name: String?,
    ): Violation =
        collectionViolationLocalizationProvider
            .notContains(
                value = value,
                element = element,
                name = name,
            ).asViolationReason()

    override fun <C : Collection<*>> notEmpty(
        value: C,
        name: String?,
    ): Violation =
        collectionViolationLocalizationProvider
            .notEmpty(
                value = value,
                name = name,
            ).asViolationReason()

    override fun <C : Collection<*>> notOfSize(
        value: C,
        size: Int,
        name: String?,
    ): Violation =
        collectionViolationLocalizationProvider
            .notOfSize(
                value = value,
                size = size,
                name = name,
            ).asViolationReason()

    override fun <C : Collection<*>> ofSize(
        value: C,
        size: Int,
        name: String?,
    ): Violation =
        collectionViolationLocalizationProvider
            .ofSize(
                value = value,
                size = size,
                name = name,
            ).asViolationReason()

    override fun <C : Collection<*>> sizeBetween(
        value: C,
        sizeRange: IntRange,
        name: String?,
    ): Violation =
        collectionViolationLocalizationProvider
            .sizeBetween(
                value = value,
                sizeRange = sizeRange,
                name = name,
            ).asViolationReason()

    override fun <C : Collection<*>> sizeNotBetween(
        value: C,
        sizeRange: IntRange,
        name: String?,
    ): Violation =
        collectionViolationLocalizationProvider
            .sizeNotBetween(
                value = value,
                sizeRange = sizeRange,
                name = name,
            ).asViolationReason()
}
