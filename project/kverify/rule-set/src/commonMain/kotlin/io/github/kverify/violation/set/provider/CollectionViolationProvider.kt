package io.github.kverify.violation.set.provider

import io.github.kverify.core.violation.Violation

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
