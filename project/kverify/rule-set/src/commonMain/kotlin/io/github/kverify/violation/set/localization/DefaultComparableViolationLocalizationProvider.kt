package io.github.kverify.violation.set.localization

internal object DefaultComparableViolationLocalizationProvider : ComparableViolationLocalizationProvider {
    override fun <T : Comparable<T>> between(
        value: T,
        range: ClosedRange<T>,
        name: String?,
    ): String {
        val displayName = resolveComparableName(name)

        return "$displayName must be between '${range.start}' and '${range.endInclusive}', but is '$value'."
    }

    override fun <T : Comparable<T>> equalTo(
        value: T,
        other: T,
        name: String?,
    ): String {
        val displayName = resolveComparableName(name)

        return "$displayName must equal '$other', but is '$value'."
    }

    override fun <T : Comparable<T>> greaterThanOrEqualTo(
        value: T,
        other: T,
        name: String?,
    ): String {
        val displayName = resolveComparableName(name)

        return "$displayName must be >= '$other', but is '$value'."
    }

    override fun <T : Comparable<T>> greaterThan(
        value: T,
        other: T,
        name: String?,
    ): String {
        val displayName = resolveComparableName(name)

        return "$displayName must be > '$other', but is '$value'."
    }

    override fun <T : Comparable<T>> lessThanOrEqualTo(
        value: T,
        other: T,
        name: String?,
    ): String {
        val displayName = resolveComparableName(name)

        return "$displayName must be <= '$other', but is '$value'."
    }

    override fun <T : Comparable<T>> lessThan(
        value: T,
        other: T,
        name: String?,
    ): String {
        val displayName = resolveComparableName(name)

        return "$displayName must be < '$other', but is '$value'."
    }

    override fun <T : Comparable<T>> notBetween(
        value: T,
        range: ClosedRange<T>,
        name: String?,
    ): String {
        val displayName = resolveComparableName(name)

        return "$displayName must NOT be between '${range.start}' and '${range.endInclusive}', but is '$value'."
    }

    override fun <T : Comparable<T>> notEqualTo(
        value: T,
        other: T,
        name: String?,
    ): String {
        val displayName = resolveComparableName(name)

        return "$displayName must NOT equal '$other'."
    }
}
