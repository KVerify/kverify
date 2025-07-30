package io.github.kverify.violation.set.localization

import io.github.kverify.violation.set.joinWithLimitAndBrackets
import io.github.kverify.violation.set.resolveCollectionName

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
        minSize: Int,
        maxSize: Int,
        name: String? = null,
    ): String

    public fun <C : Collection<*>> sizeNotBetween(
        value: C,
        minSize: Int,
        maxSize: Int,
        name: String? = null,
    ): String

    public companion object {
        // Hides unwanted import hints
        public val Default: CollectionViolationLocalizationProvider = DefaultCollectionViolationLocalizationProvider
    }
}

@Suppress("TooManyFunctions")
internal object DefaultCollectionViolationLocalizationProvider : CollectionViolationLocalizationProvider {
    override fun <E, C : Collection<E>> containsAll(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)
        val elementsAsString = elements.joinWithLimitAndBrackets()
        val missingElements = elements.filterNot { it in value }.joinWithLimitAndBrackets()

        return "$displayName must contain all of the following elements: $elementsAsString, but these are missing: $missingElements."
    }

    override fun <E, C : Collection<E>> containsNone(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)
        val elementsAsString = elements.joinWithLimitAndBrackets()
        val forbiddenElements = value.filter { it in elements }.joinWithLimitAndBrackets()

        return "$displayName must not contain these elements: $elementsAsString, but these are present: $forbiddenElements."
    }

    override fun <E, C : Collection<E>> containsOnly(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)
        val elementsAsString = elements.joinWithLimitAndBrackets()
        val unexpectedElements = value.filter { it !in elements }.joinWithLimitAndBrackets()

        return "$displayName must contain only the elements: $elementsAsString, but it also contains: $unexpectedElements."
    }

    override fun <E, C : Collection<E>> contains(
        value: C,
        element: E,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName must contain the element: '$element', but it does not."
    }

    override fun <C : Collection<*>> distinct(
        value: C,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)
        val duplicates =
            value
                .groupingBy { it }
                .eachCount()
                .filter { it.value > 1 }
                .keys
                .joinWithLimitAndBrackets()

        return "$displayName must be distinct, but it has duplicates: $duplicates"
    }

    override fun <C : Collection<*>> maxSize(
        value: C,
        maxSize: Int,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName must have $maxSize elements at most, but it has ${value.size}."
    }

    override fun <C : Collection<*>> minSize(
        value: C,
        minSize: Int,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName must have at least $minSize elements, but it has ${value.size}."
    }

    override fun <E, C : Collection<E>> notContains(
        value: C,
        element: E,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName must not contain the element: '$element', but it does."
    }

    override fun <C : Collection<*>> notEmpty(
        value: C,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName must not be empty, but it is."
    }

    override fun <C : Collection<*>> notOfSize(
        value: C,
        size: Int,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName must not have exactly $size elements, but it does."
    }

    override fun <C : Collection<*>> ofSize(
        value: C,
        size: Int,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName must have exactly $size elements, but it has ${value.size}."
    }

    override fun <C : Collection<*>> sizeBetween(
        value: C,
        minSize: Int,
        maxSize: Int,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName size must be between $minSize and $maxSize (inclusive), but it is ${value.size}."
    }

    override fun <C : Collection<*>> sizeNotBetween(
        value: C,
        minSize: Int,
        maxSize: Int,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName size must NOT be between $minSize and $maxSize (inclusive), but it is ${value.size}."
    }
}
