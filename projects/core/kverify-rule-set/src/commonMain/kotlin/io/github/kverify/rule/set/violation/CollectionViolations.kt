package io.github.kverify.rule.set.violation

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolation

@Suppress("TooManyFunctions")
interface CollectionViolations {
    fun <C : Collection<*>> ofSize(
        size: Int,
        value: C,
        name: String = "collection",
    ): Violation = "'$name' must have exactly $size elements, but it has ${value.size}.".asViolation()

    fun <C : Collection<*>> notOfSize(
        size: Int,
        value: C,
        name: String = "collection",
    ): Violation = "'$name' must not have exactly $size elements, but it does.".asViolation()

    fun <C : Collection<*>> maxSize(
        size: Int,
        value: C,
        name: String = "collection",
    ): Violation = "'$name' must have at most $size elements, but it has ${value.size}.".asViolation()

    fun <C : Collection<*>> minSize(
        size: Int,
        value: C,
        name: String = "collection",
    ): Violation = "'$name' must have at least $size elements, but it has ${value.size}.".asViolation()

    fun <C : Collection<*>> sizeBetween(
        range: IntRange,
        value: C,
        name: String = "collection",
    ): Violation = "'$name' must have a size within the range $range, but it has ${value.size}.".asViolation()

    fun <C : Collection<*>> sizeNotBetween(
        range: IntRange,
        value: C,
        name: String = "collection",
    ): Violation = "'$name' must not have a size within the range $range, but it has ${value.size}.".asViolation()

    fun <T, C : Collection<T>> containsAll(
        elements: Collection<T>,
        value: C,
        name: String = "collection",
    ): Violation {
        val missingElements = elements.filterNot { it in value }.joinToString(", ")
        return if (missingElements.isNotEmpty()) {
            "'$name' must contain all of the following elements: [$missingElements], but some are missing."
                .asViolation()
        } else {
            "'$name' must contain all specified elements.".asViolation()
        }
    }

    fun <T, C : Collection<T>> containsNone(
        elements: Collection<T>,
        value: C,
        name: String = "collection",
    ): Violation {
        val forbiddenElements = elements.filter { it in value }.joinToString(", ")
        return if (forbiddenElements.isNotEmpty()) {
            "'$name' must not contain these elements: [$forbiddenElements], but they are present.".asViolation()
        } else {
            "'$name' must not contain any of the specified elements.".asViolation()
        }
    }

    fun <T, C : Collection<T>> contains(
        element: T,
        value: C,
        name: String = "collection",
    ): Violation {
        val valueString = value.joinToString(", ")
        return "'$name' must contain the element '$element', but it only contains: [$valueString].".asViolation()
    }

    fun <T, C : Collection<T>> notContains(
        element: T,
        value: C,
        name: String = "collection",
    ): Violation {
        val valueString = value.joinToString(", ")
        return "'$name' must not contain the element '$element', but it contains: [$valueString].".asViolation()
    }

    fun <T, C : Collection<T>> containsOnly(
        elements: Collection<T>,
        value: C,
        name: String = "collection",
    ): Violation {
        val unexpectedElements = value.filterNot { it in elements }.joinToString(", ")
        val missingElements = elements.filterNot { it in value }.joinToString(", ")
        return if (unexpectedElements.isNotEmpty() || missingElements.isNotEmpty()) {
            "'$name' must contain only the elements: [${
                elements.joinToString(
                    ", ",
                )
            }], but it also contains: [$unexpectedElements] and is missing: [$missingElements].".asViolation()
        } else {
            "'$name' must only contain the specified elements.".asViolation()
        }
    }

    fun <C : Collection<*>> notEmpty(
        value: C,
        name: String = "collection",
    ): Violation = "'$name' must not be empty, but it is.".asViolation()

    fun <C : Collection<*>> distinct(
        value: C,
        name: String = "collection",
    ): Violation {
        val duplicates =
            value
                .groupingBy { it }
                .eachCount()
                .filter { it.value > 1 }
                .keys
                .joinToString(", ")
        return if (duplicates.isNotEmpty()) {
            "'$name' must contain only distinct elements, but it has duplicates: [$duplicates].".asViolation()
        } else {
            "'$name' must be distinct.".asViolation()
        }
    }

    companion object Default : CollectionViolations
}
