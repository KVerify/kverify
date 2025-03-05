package io.github.kverify.rule.set.violation

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolation

interface ComparableViolations {
    fun <T : Comparable<T>> equalTo(
        other: T,
        value: T,
        name: String = "comparable",
    ): Violation = "'$name' must be equal to '$other', but it is '$value'.".asViolation()

    fun <T : Comparable<T>> notEqualTo(
        other: T,
        value: T,
        name: String = "comparable",
    ): Violation = "'$name' must not be equal to '$other', but it is.".asViolation()

    fun <T : Comparable<T>> greaterThan(
        other: T,
        value: T,
        name: String = "comparable",
    ): Violation = "'$name' must be greater than '$other', but it is '$value'.".asViolation()

    fun <T : Comparable<T>> greaterThanOrEqualTo(
        other: T,
        value: T,
        name: String = "comparable",
    ): Violation = "'$name' must be greater than or equal to '$other', but it is '$value'.".asViolation()

    fun <T : Comparable<T>> lessThan(
        other: T,
        value: T,
        name: String = "comparable",
    ): Violation = "'$name' must be less than '$other', but it is '$value'.".asViolation()

    fun <T : Comparable<T>> lessThanOrEqualTo(
        other: T,
        value: T,
        name: String = "comparable",
    ): Violation = "'$name' must be less than or equal to '$other', but it is '$value'.".asViolation()

    fun <T : Comparable<T>> between(
        range: ClosedRange<T>,
        value: T,
        name: String = "comparable",
    ): Violation = "'$name' must be in range '$range', but it is '$value'.".asViolation()

    fun <T : Comparable<T>> notBetween(
        range: ClosedRange<T>,
        value: T,
        name: String = "comparable",
    ): Violation = "'$name' must not be in range '$range', but it is '$value'.".asViolation()

    companion object Default : ComparableViolations
}
