package io.github.kverify.violation.set.localization

public interface ComparableViolationLocalizationProvider {
    public fun <T : Comparable<T>> between(
        value: T,
        range: ClosedRange<T>,
        name: String? = null,
    ): String

    public fun <T : Comparable<T>> equalTo(
        value: T,
        other: T,
        name: String? = null,
    ): String

    public fun <T : Comparable<T>> greaterThanOrEqualTo(
        value: T,
        other: T,
        name: String? = null,
    ): String

    public fun <T : Comparable<T>> greaterThan(
        value: T,
        other: T,
        name: String? = null,
    ): String

    public fun <T : Comparable<T>> lessThanOrEqualTo(
        value: T,
        other: T,
        name: String? = null,
    ): String

    public fun <T : Comparable<T>> lessThan(
        value: T,
        other: T,
        name: String? = null,
    ): String

    public fun <T : Comparable<T>> notBetween(
        value: T,
        range: ClosedRange<T>,
        name: String? = null,
    ): String

    public fun <T : Comparable<T>> notEqualTo(
        value: T,
        other: T,
        name: String? = null,
    ): String

    public companion object {
        public val Default: ComparableViolationLocalizationProvider = DefaultComparableViolationLocalizationProvider
    }
}
