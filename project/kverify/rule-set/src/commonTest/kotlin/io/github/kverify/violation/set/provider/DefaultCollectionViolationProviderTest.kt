package io.github.kverify.violation.set.provider

import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultCollectionViolationProviderTest {
    private val localizationProvider = object : CollectionViolationLocalizationProvider {
        override fun <E, C : Collection<E>> containsAll(value: C, elements: Collection<E>, name: String?): String =
            "containsAll: $name"

        override fun <E, C : Collection<E>> containsNone(value: C, elements: Collection<E>, name: String?): String =
            "containsNone: $name"

        override fun <E, C : Collection<E>> containsOnly(value: C, elements: Collection<E>, name: String?): String =
            "containsOnly: $name"

        override fun <E, C : Collection<E>> contains(value: C, element: E, name: String?): String =
            "contains: $name"

        override fun <C : Collection<*>> distinct(value: C, name: String?): String =
            "distinct: $name"

        override fun <C : Collection<*>> maxSize(value: C, maxSize: Int, name: String?): String =
            "maxSize: $name"

        override fun <C : Collection<*>> minSize(value: C, minSize: Int, name: String?): String =
            "minSize: $name"

        override fun <E, C : Collection<E>> notContains(value: C, element: E, name: String?): String =
            "notContains: $name"

        override fun <C : Collection<*>> notEmpty(value: C, name: String?): String =
            "notEmpty: $name"

        override fun <C : Collection<*>> notOfSize(value: C, size: Int, name: String?): String =
            "notOfSize: $name"

        override fun <C : Collection<*>> ofSize(value: C, size: Int, name: String?): String =
            "ofSize: $name"

        override fun <C : Collection<*>> sizeBetween(value: C, sizeRange: IntRange, name: String?): String =
            "sizeBetween: $name"

        override fun <C : Collection<*>> sizeNotBetween(value: C, sizeRange: IntRange, name: String?): String =
            "sizeNotBetween: $name"
    }

    private val provider = DefaultCollectionViolationProvider(localizationProvider)

    @Test
    fun containsAllDelegatesToLocalizationProvider() {
        val violation = provider.containsAll(listOf("a"), listOf("a", "b"), "items")
        assertEquals("containsAll: items", violation.reason)
    }

    @Test
    fun containsAllWithNullName() {
        val violation = provider.containsAll(listOf(1), listOf(1, 2), null)
        assertEquals("containsAll: null", violation.reason)
    }

    @Test
    fun containsNoneDelegatesToLocalizationProvider() {
        val violation = provider.containsNone(listOf("a", "b"), listOf("b"), "items")
        assertEquals("containsNone: items", violation.reason)
    }

    @Test
    fun containsNoneWithNullName() {
        val violation = provider.containsNone(listOf(1, 2), listOf(3), null)
        assertEquals("containsNone: null", violation.reason)
    }

    @Test
    fun containsOnlyDelegatesToLocalizationProvider() {
        val violation = provider.containsOnly(listOf("a", "b"), listOf("a"), "items")
        assertEquals("containsOnly: items", violation.reason)
    }

    @Test
    fun containsOnlyWithNullName() {
        val violation = provider.containsOnly(listOf(1, 2), listOf(1), null)
        assertEquals("containsOnly: null", violation.reason)
    }

    @Test
    fun containsDelegatesToLocalizationProvider() {
        val violation = provider.contains(listOf("a"), "b", "items")
        assertEquals("contains: items", violation.reason)
    }

    @Test
    fun containsWithNullName() {
        val violation = provider.contains(listOf(1), 2, null)
        assertEquals("contains: null", violation.reason)
    }

    @Test
    fun distinctDelegatesToLocalizationProvider() {
        val violation = provider.distinct(listOf("a", "a"), "items")
        assertEquals("distinct: items", violation.reason)
    }

    @Test
    fun distinctWithNullName() {
        val violation = provider.distinct(listOf(1, 1), null)
        assertEquals("distinct: null", violation.reason)
    }

    @Test
    fun maxSizeDelegatesToLocalizationProvider() {
        val violation = provider.maxSize(listOf("a", "b", "c"), 2, "items")
        assertEquals("maxSize: items", violation.reason)
    }

    @Test
    fun maxSizeWithNullName() {
        val violation = provider.maxSize(listOf(1, 2, 3), 2, null)
        assertEquals("maxSize: null", violation.reason)
    }

    @Test
    fun minSizeDelegatesToLocalizationProvider() {
        val violation = provider.minSize(listOf("a"), 3, "items")
        assertEquals("minSize: items", violation.reason)
    }

    @Test
    fun minSizeWithNullName() {
        val violation = provider.minSize(listOf(1), 5, null)
        assertEquals("minSize: null", violation.reason)
    }

    @Test
    fun notContainsDelegatesToLocalizationProvider() {
        val violation = provider.notContains(listOf("a", "b"), "b", "items")
        assertEquals("notContains: items", violation.reason)
    }

    @Test
    fun notContainsWithNullName() {
        val violation = provider.notContains(listOf(1, 2), 2, null)
        assertEquals("notContains: null", violation.reason)
    }

    @Test
    fun notEmptyDelegatesToLocalizationProvider() {
        val violation = provider.notEmpty(emptyList<String>(), "items")
        assertEquals("notEmpty: items", violation.reason)
    }

    @Test
    fun notEmptyWithNullName() {
        val violation = provider.notEmpty(emptyList<Int>(), null)
        assertEquals("notEmpty: null", violation.reason)
    }

    @Test
    fun notOfSizeDelegatesToLocalizationProvider() {
        val violation = provider.notOfSize(listOf("a", "b"), 2, "items")
        assertEquals("notOfSize: items", violation.reason)
    }

    @Test
    fun notOfSizeWithNullName() {
        val violation = provider.notOfSize(listOf(1, 2), 2, null)
        assertEquals("notOfSize: null", violation.reason)
    }

    @Test
    fun ofSizeDelegatesToLocalizationProvider() {
        val violation = provider.ofSize(listOf("a"), 3, "items")
        assertEquals("ofSize: items", violation.reason)
    }

    @Test
    fun ofSizeWithNullName() {
        val violation = provider.ofSize(listOf(1), 5, null)
        assertEquals("ofSize: null", violation.reason)
    }

    @Test
    fun sizeBetweenDelegatesToLocalizationProvider() {
        val violation = provider.sizeBetween(listOf("a"), 2..5, "items")
        assertEquals("sizeBetween: items", violation.reason)
    }

    @Test
    fun sizeBetweenWithNullName() {
        val violation = provider.sizeBetween(listOf(1), 3..7, null)
        assertEquals("sizeBetween: null", violation.reason)
    }

    @Test
    fun sizeNotBetweenDelegatesToLocalizationProvider() {
        val violation = provider.sizeNotBetween(listOf("a", "b"), 1..3, "items")
        assertEquals("sizeNotBetween: items", violation.reason)
    }

    @Test
    fun sizeNotBetweenWithNullName() {
        val violation = provider.sizeNotBetween(listOf(1, 2), 1..5, null)
        assertEquals("sizeNotBetween: null", violation.reason)
    }

    @Test
    fun usesDefaultLocalizationProviderWhenNotSpecified() {
        val defaultProvider = DefaultCollectionViolationProvider()
        val violation = defaultProvider.contains(listOf("a"), "b", "items")
        assertTrue(violation.reason.contains("items"))
    }
}
