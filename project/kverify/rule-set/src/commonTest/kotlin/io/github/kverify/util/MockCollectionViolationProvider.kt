package io.github.kverify.util

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.ViolationReason
import io.github.kverify.violation.set.provider.CollectionViolationProvider

class MockCollectionViolationProvider : CollectionViolationProvider {
    override fun <E, C : Collection<E>> containsAll(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): Violation = ViolationReason("containsAll")

    override fun <E, C : Collection<E>> containsNone(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): Violation = ViolationReason("containsNone")

    override fun <E, C : Collection<E>> containsOnly(
        value: C,
        elements: Collection<E>,
        name: String?,
    ): Violation = ViolationReason("containsOnly")

    override fun <E, C : Collection<E>> contains(
        value: C,
        element: E,
        name: String?,
    ): Violation = ViolationReason("contains")

    override fun <C : Collection<*>> distinct(
        value: C,
        name: String?,
    ): Violation = ViolationReason("distinct")

    override fun <C : Collection<*>> maxSize(
        value: C,
        maxSize: Int,
        name: String?,
    ): Violation = ViolationReason("maxSize")

    override fun <C : Collection<*>> minSize(
        value: C,
        minSize: Int,
        name: String?,
    ): Violation = ViolationReason("minSize")

    override fun <E, C : Collection<E>> notContains(
        value: C,
        element: E,
        name: String?,
    ): Violation = ViolationReason("notContains")

    override fun <C : Collection<*>> notEmpty(
        value: C,
        name: String?,
    ): Violation = ViolationReason("notEmpty")

    override fun <C : Collection<*>> notOfSize(
        value: C,
        size: Int,
        name: String?,
    ): Violation = ViolationReason("notOfSize")

    override fun <C : Collection<*>> ofSize(
        value: C,
        size: Int,
        name: String?,
    ): Violation = ViolationReason("ofSize")

    override fun <C : Collection<*>> sizeBetween(
        value: C,
        sizeRange: IntRange,
        name: String?,
    ): Violation = ViolationReason("sizeBetween")

    override fun <C : Collection<*>> sizeNotBetween(
        value: C,
        sizeRange: IntRange,
        name: String?,
    ): Violation = ViolationReason("sizeNotBetween")
}
