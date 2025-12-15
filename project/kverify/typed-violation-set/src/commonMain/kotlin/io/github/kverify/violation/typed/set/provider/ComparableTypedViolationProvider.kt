package io.github.kverify.violation.typed.set.provider

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider
import io.github.kverify.violation.set.provider.ComparableViolationProvider
import io.github.kverify.violation.typed.set.comparable.ComparableBetweenViolation
import io.github.kverify.violation.typed.set.comparable.ComparableEqualToViolation
import io.github.kverify.violation.typed.set.comparable.ComparableGreaterThanOrEqualToViolation
import io.github.kverify.violation.typed.set.comparable.ComparableGreaterThanViolation
import io.github.kverify.violation.typed.set.comparable.ComparableLessThanOrEqualToViolation
import io.github.kverify.violation.typed.set.comparable.ComparableLessThanViolation
import io.github.kverify.violation.typed.set.comparable.ComparableNotBetweenViolation
import io.github.kverify.violation.typed.set.comparable.ComparableNotEqualToViolation

public class ComparableTypedViolationProvider(
    public val localization: ComparableViolationLocalizationProvider = ComparableViolationLocalizationProvider.Default,
) : ComparableViolationProvider {
    override fun <T : Comparable<T>> between(
        value: T,
        range: ClosedRange<T>,
        name: String?,
    ): Violation =
        ComparableBetweenViolation(
            value = value,
            range = range,
            name = name,
            reason =
                localization.between(
                    value = value,
                    range = range,
                    name = name,
                ),
        )

    override fun <T : Comparable<T>> equalTo(
        value: T,
        other: T,
        name: String?,
    ): Violation =
        ComparableEqualToViolation(
            value = value,
            other = other,
            name = name,
            reason =
                localization.equalTo(
                    value = value,
                    other = other,
                    name = name,
                ),
        )

    override fun <T : Comparable<T>> greaterThanOrEqualTo(
        value: T,
        other: T,
        name: String?,
    ): Violation =
        ComparableGreaterThanOrEqualToViolation(
            value = value,
            other = other,
            name = name,
            reason =
                localization.greaterThanOrEqualTo(
                    value = value,
                    other = other,
                    name = name,
                ),
        )

    override fun <T : Comparable<T>> greaterThan(
        value: T,
        other: T,
        name: String?,
    ): Violation =
        ComparableGreaterThanViolation(
            value = value,
            other = other,
            name = name,
            reason =
                localization.greaterThan(
                    value = value,
                    other = other,
                    name = name,
                ),
        )

    override fun <T : Comparable<T>> lessThanOrEqualTo(
        value: T,
        other: T,
        name: String?,
    ): Violation =
        ComparableLessThanOrEqualToViolation(
            value = value,
            other = other,
            name = name,
            reason =
                localization.lessThanOrEqualTo(
                    value = value,
                    other = other,
                    name = name,
                ),
        )

    override fun <T : Comparable<T>> lessThan(
        value: T,
        other: T,
        name: String?,
    ): Violation =
        ComparableLessThanViolation(
            value = value,
            other = other,
            name = name,
            reason =
                localization.lessThan(
                    value = value,
                    other = other,
                    name = name,
                ),
        )

    override fun <T : Comparable<T>> notBetween(
        value: T,
        range: ClosedRange<T>,
        name: String?,
    ): Violation =
        ComparableNotBetweenViolation(
            value = value,
            range = range,
            name = name,
            reason =
                localization.notBetween(
                    value = value,
                    range = range,
                    name = name,
                ),
        )

    override fun <T : Comparable<T>> notEqualTo(
        value: T,
        other: T,
        name: String?,
    ): Violation =
        ComparableNotEqualToViolation(
            value = value,
            other = other,
            name = name,
            reason =
                localization.notEqualTo(
                    value = value,
                    other = other,
                    name = name,
                ),
        )

    public companion object {
        public val Default: ComparableTypedViolationProvider = ComparableTypedViolationProvider()
    }
}
