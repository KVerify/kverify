package io.github.kverify.violation.set.provider

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolationReason
import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider

public interface ComparableViolationProvider {
    public fun <T : Comparable<T>> between(
        value: T,
        min: T,
        max: T,
        name: String? = null,
    ): Violation

    public fun <T : Comparable<T>> equalTo(
        value: T,
        other: T,
        name: String? = null,
    ): Violation

    public fun <T : Comparable<T>> greaterThanOrEqualTo(
        value: T,
        other: T,
        name: String? = null,
    ): Violation

    public fun <T : Comparable<T>> greaterThan(
        value: T,
        other: T,
        name: String? = null,
    ): Violation

    public fun <T : Comparable<T>> lessThanOrEqualTo(
        value: T,
        other: T,
        name: String? = null,
    ): Violation

    public fun <T : Comparable<T>> lessThan(
        value: T,
        other: T,
        name: String? = null,
    ): Violation

    public fun <T : Comparable<T>> notBetween(
        value: T,
        min: T,
        max: T,
        name: String? = null,
    ): Violation

    public fun <T : Comparable<T>> notEqualTo(
        value: T,
        other: T,
        name: String? = null,
    ): Violation

    public companion object {
        public val Default: ComparableViolationProvider = ComparableViolations()
    }
}

public class ComparableViolations(
    public val comparableViolationLocalizationProvider: ComparableViolationLocalizationProvider =
        ComparableViolationLocalizationProvider.Default,
) : ComparableViolationProvider {
    override fun <T : Comparable<T>> between(
        value: T,
        min: T,
        max: T,
        name: String?,
    ): Violation =
        comparableViolationLocalizationProvider
            .between(
                value = value,
                min = min,
                max = max,
                name = name,
            ).asViolationReason()

    override fun <T : Comparable<T>> equalTo(
        value: T,
        other: T,
        name: String?,
    ): Violation =
        comparableViolationLocalizationProvider
            .equalTo(
                value = value,
                other = other,
                name = name,
            ).asViolationReason()

    override fun <T : Comparable<T>> greaterThanOrEqualTo(
        value: T,
        other: T,
        name: String?,
    ): Violation =
        comparableViolationLocalizationProvider
            .greaterThanOrEqualTo(
                value = value,
                other = other,
                name = name,
            ).asViolationReason()

    override fun <T : Comparable<T>> greaterThan(
        value: T,
        other: T,
        name: String?,
    ): Violation =
        comparableViolationLocalizationProvider
            .greaterThan(
                value = value,
                other = other,
                name = name,
            ).asViolationReason()

    override fun <T : Comparable<T>> lessThanOrEqualTo(
        value: T,
        other: T,
        name: String?,
    ): Violation =
        comparableViolationLocalizationProvider
            .lessThanOrEqualTo(
                value = value,
                other = other,
                name = name,
            ).asViolationReason()

    override fun <T : Comparable<T>> lessThan(
        value: T,
        other: T,
        name: String?,
    ): Violation =
        comparableViolationLocalizationProvider
            .lessThan(
                value = value,
                other = other,
                name = name,
            ).asViolationReason()

    override fun <T : Comparable<T>> notBetween(
        value: T,
        min: T,
        max: T,
        name: String?,
    ): Violation =
        comparableViolationLocalizationProvider
            .notBetween(
                value = value,
                min = min,
                max = max,
                name = name,
            ).asViolationReason()

    override fun <T : Comparable<T>> notEqualTo(
        value: T,
        other: T,
        name: String?,
    ): Violation =
        comparableViolationLocalizationProvider
            .notEqualTo(
                value = value,
                other = other,
                name = name,
            ).asViolationReason()
}
