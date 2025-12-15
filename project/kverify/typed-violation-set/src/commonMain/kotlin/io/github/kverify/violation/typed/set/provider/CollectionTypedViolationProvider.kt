package io.github.kverify.violation.typed.set.provider

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.set.provider.CollectionViolationProvider
import io.github.kverify.violation.typed.set.collection.CollectionContainsAllViolation
import io.github.kverify.violation.typed.set.collection.CollectionContainsNoneViolation
import io.github.kverify.violation.typed.set.collection.CollectionContainsOnlyViolation
import io.github.kverify.violation.typed.set.collection.CollectionContainsViolation
import io.github.kverify.violation.typed.set.collection.CollectionDistinctViolation
import io.github.kverify.violation.typed.set.collection.CollectionMaxSizeViolation
import io.github.kverify.violation.typed.set.collection.CollectionMinSizeViolation
import io.github.kverify.violation.typed.set.collection.CollectionNotContainsViolation
import io.github.kverify.violation.typed.set.collection.CollectionNotEmptyViolation
import io.github.kverify.violation.typed.set.collection.CollectionNotOfSizeViolation
import io.github.kverify.violation.typed.set.collection.CollectionOfSizeViolation
import io.github.kverify.violation.typed.set.collection.CollectionSizeBetweenViolation
import io.github.kverify.violation.typed.set.collection.CollectionSizeNotBetweenViolation

@Suppress("TooManyFunctions")
public class CollectionTypedViolationProvider(
    public val localizationProvider: CollectionViolationLocalizationProvider =
        CollectionViolationLocalizationProvider.Default,
) : CollectionViolationProvider {
    override fun <E, C : Collection<E>> containsAll(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): Violation =
        CollectionContainsAllViolation(
            value = value,
            elements = elements,
            name = name,
            reason =
                localizationProvider.containsAll(
                    value = value,
                    elements = elements,
                    name = name,
                ),
        )

    override fun <E, C : Collection<E>> containsNone(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): Violation =
        CollectionContainsNoneViolation(
            value = value,
            elements = elements,
            name = name,
            reason =
                localizationProvider.containsNone(
                    value = value,
                    elements = elements,
                    name = name,
                ),
        )

    override fun <E, C : Collection<E>> containsOnly(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): Violation =
        CollectionContainsOnlyViolation(
            value = value,
            elements = elements,
            name = name,
            reason =
                localizationProvider.containsOnly(
                    value = value,
                    elements = elements,
                    name = name,
                ),
        )

    override fun <E, C : Collection<E>> contains(
        value: C,
        element: E,
        name: String?,
    ): Violation =
        CollectionContainsViolation(
            value = value,
            element = element,
            name = name,
            reason =
                localizationProvider.contains(
                    value = value,
                    element = element,
                    name = name,
                ),
        )

    override fun <C : Collection<*>> distinct(
        value: C,
        name: String?,
    ): Violation =
        CollectionDistinctViolation(
            value = value,
            name = name,
            reason =
                localizationProvider.distinct(
                    value = value,
                    name = name,
                ),
        )

    override fun <C : Collection<*>> maxSize(
        value: C,
        maxSize: Int,
        name: String?,
    ): Violation =
        CollectionMaxSizeViolation(
            value = value,
            maxSize = maxSize,
            name = name,
            reason =
                localizationProvider.maxSize(
                    value = value,
                    maxSize = maxSize,
                    name = name,
                ),
        )

    override fun <C : Collection<*>> minSize(
        value: C,
        minSize: Int,
        name: String?,
    ): Violation =
        CollectionMinSizeViolation(
            value = value,
            minSize = minSize,
            name = name,
            reason =
                localizationProvider.minSize(
                    value = value,
                    minSize = minSize,
                    name = name,
                ),
        )

    override fun <E, C : Collection<E>> notContains(
        value: C,
        element: E,
        name: String?,
    ): Violation =
        CollectionNotContainsViolation(
            value = value,
            element = element,
            name = name,
            reason =
                localizationProvider.notContains(
                    value = value,
                    element = element,
                    name = name,
                ),
        )

    override fun <C : Collection<*>> notEmpty(
        value: C,
        name: String?,
    ): Violation =
        CollectionNotEmptyViolation(
            value = value,
            name = name,
            reason =
                localizationProvider.notEmpty(
                    value = value,
                    name = name,
                ),
        )

    override fun <C : Collection<*>> notOfSize(
        value: C,
        size: Int,
        name: String?,
    ): Violation =
        CollectionNotOfSizeViolation(
            value = value,
            size = size,
            name = name,
            reason =
                localizationProvider.notOfSize(
                    value = value,
                    size = size,
                    name = name,
                ),
        )

    override fun <C : Collection<*>> ofSize(
        value: C,
        size: Int,
        name: String?,
    ): Violation =
        CollectionOfSizeViolation(
            value = value,
            size = size,
            name = name,
            reason =
                localizationProvider.ofSize(
                    value = value,
                    size = size,
                    name = name,
                ),
        )

    override fun <C : Collection<*>> sizeBetween(
        value: C,
        sizeRange: IntRange,
        name: String?,
    ): Violation =
        CollectionSizeBetweenViolation(
            value = value,
            sizeRange = sizeRange,
            name = name,
            reason =
                localizationProvider.sizeBetween(
                    value = value,
                    sizeRange = sizeRange,
                    name = name,
                ),
        )

    override fun <C : Collection<*>> sizeNotBetween(
        value: C,
        sizeRange: IntRange,
        name: String?,
    ): Violation =
        CollectionSizeNotBetweenViolation(
            value = value,
            sizeRange = sizeRange,
            name = name,
            reason =
                localizationProvider.sizeNotBetween(
                    value = value,
                    sizeRange = sizeRange,
                    name = name,
                ),
        )

    public companion object {
        public val Default: CollectionViolationProvider = CollectionTypedViolationProvider()
    }
}
