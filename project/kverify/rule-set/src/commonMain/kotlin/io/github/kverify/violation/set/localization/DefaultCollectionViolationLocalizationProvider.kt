package io.github.kverify.violation.set.localization

@Suppress("TooManyFunctions")
internal object DefaultCollectionViolationLocalizationProvider : CollectionViolationLocalizationProvider {
    override fun <E, C : Collection<E>> containsAll(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)
        val missingElements = elements.filterNot { it in value }.joinWithLimitAndBrackets()
        val elements = elements.joinWithLimitAndBrackets()

        return "$displayName must contain all elements: $elements, but missing: $missingElements."
    }

    override fun <E, C : Collection<E>> containsNone(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)
        val forbiddenElements = value.filter { it in elements }.joinWithLimitAndBrackets()
        val elements = elements.joinWithLimitAndBrackets()

        return "$displayName must not contain elements: $elements, but found: $forbiddenElements."
    }

    override fun <E, C : Collection<E>> containsOnly(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)
        val unexpectedElements = value.filter { it !in elements }.joinWithLimitAndBrackets()
        val elements = elements.joinWithLimitAndBrackets()

        return "$displayName must contain only elements: $elements, but also contains: $unexpectedElements."
    }

    override fun <E, C : Collection<E>> contains(
        value: C,
        element: E,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName must contain element: '$element'."
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

        return "$displayName must be distinct, but has duplicates: $duplicates."
    }

    override fun <C : Collection<*>> maxSize(
        value: C,
        maxSize: Int,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName size must be at most $maxSize, but is ${value.size}."
    }

    override fun <C : Collection<*>> minSize(
        value: C,
        minSize: Int,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName size must be at least $minSize, but is ${value.size}."
    }

    override fun <E, C : Collection<E>> notContains(
        value: C,
        element: E,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName must NOT contain element: '$element'."
    }

    override fun <C : Collection<*>> notEmpty(
        value: C,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName must not be empty."
    }

    override fun <C : Collection<*>> notOfSize(
        value: C,
        size: Int,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName size must NOT be $size."
    }

    override fun <C : Collection<*>> ofSize(
        value: C,
        size: Int,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName size must be exactly $size, but is ${value.size}."
    }

    override fun <C : Collection<*>> sizeBetween(
        value: C,
        sizeRange: IntRange,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName size must be between ${sizeRange.first} and ${sizeRange.last}, " +
            "but is ${value.size}."
    }

    override fun <C : Collection<*>> sizeNotBetween(
        value: C,
        sizeRange: IntRange,
        name: String?,
    ): String {
        val displayName = resolveCollectionName(name)

        return "$displayName size must NOT be between ${sizeRange.first} and ${sizeRange.last}, " +
            "but is ${value.size}."
    }
}
