package io.github.kverify.violation.set.localization

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultCollectionViolationLocalizationProviderTest {
    private val provider = DefaultCollectionViolationLocalizationProvider

    @Test
    fun containsAllWithCustomName() {
        val result = provider.containsAll(listOf("a", "b"), listOf("a", "b", "c"), "items")
        assertEquals("'items' must contain all of the following elements: [a, b, c], but these are missing: [c].", result)
    }

    @Test
    fun containsAllWithNullName() {
        val result = provider.containsAll(listOf("a"), listOf("a", "b"), null)
        assertEquals("'collection' must contain all of the following elements: [a, b], but these are missing: [b].", result)
    }

    @Test
    fun containsAllWithMultipleMissingElements() {
        val result = provider.containsAll(listOf("a"), listOf("a", "b", "c", "d"), "myList")
        assertTrue(result.contains("[b, c, d]"))
        assertTrue(result.contains("'myList'"))
    }

    @Test
    fun containsNoneWithCustomName() {
        val result = provider.containsNone(listOf("a", "b", "c"), listOf("b", "d"), "items")
        assertEquals("'items' must not contain these elements: [b, d], but these are present: [b].", result)
    }

    @Test
    fun containsNoneWithNullName() {
        val result = provider.containsNone(listOf("x", "y"), listOf("x"), null)
        assertEquals("'collection' must not contain these elements: [x], but these are present: [x].", result)
    }

    @Test
    fun containsNoneWithMultipleForbiddenElements() {
        val result = provider.containsNone(listOf("a", "b", "c"), listOf("a", "b", "c"), "myList")
        assertTrue(result.contains("[a, b, c]"))
        assertTrue(result.contains("'myList'"))
    }

    @Test
    fun containsOnlyWithCustomName() {
        val result = provider.containsOnly(listOf("a", "b", "c"), listOf("a", "b"), "items")
        assertEquals("'items' must contain only the elements: [a, b], but it also contains: [c].", result)
    }

    @Test
    fun containsOnlyWithNullName() {
        val result = provider.containsOnly(listOf("x", "y"), listOf("x"), null)
        assertEquals("'collection' must contain only the elements: [x], but it also contains: [y].", result)
    }

    @Test
    fun containsOnlyWithMultipleUnexpectedElements() {
        val result = provider.containsOnly(listOf("a", "b", "c", "d"), listOf("a"), "myList")
        assertTrue(result.contains("[b, c, d]"))
        assertTrue(result.contains("'myList'"))
    }

    @Test
    fun containsWithCustomName() {
        val result = provider.contains(listOf("a", "b"), "c", "items")
        assertEquals("'items' must contain the element: 'c', but it does not.", result)
    }

    @Test
    fun containsWithNullName() {
        val result = provider.contains(listOf("x"), "y", null)
        assertEquals("'collection' must contain the element: 'y', but it does not.", result)
    }

    @Test
    fun containsWithIntegerElement() {
        val result = provider.contains(listOf(1, 2), 3, "numbers")
        assertEquals("'numbers' must contain the element: '3', but it does not.", result)
    }

    @Test
    fun distinctWithCustomName() {
        val result = provider.distinct(listOf("a", "b", "a", "c", "b"), "items")
        assertTrue(result.contains("'items'"))
        assertTrue(result.contains("must be distinct"))
        assertTrue(result.contains("[a, b]") || result.contains("[b, a]"))
    }

    @Test
    fun distinctWithNullName() {
        val result = provider.distinct(listOf(1, 2, 1), null)
        assertTrue(result.contains("'collection'"))
        assertTrue(result.contains("[1]"))
    }

    @Test
    fun distinctWithMultipleDuplicates() {
        val result = provider.distinct(listOf("a", "a", "b", "b", "c", "c"), "myList")
        assertTrue(result.contains("'myList'"))
        assertTrue(result.contains("duplicates"))
    }

    @Test
    fun maxSizeWithCustomName() {
        val result = provider.maxSize(listOf("a", "b", "c", "d"), 3, "items")
        assertEquals("'items' must have 3 elements at most, but it has 4.", result)
    }

    @Test
    fun maxSizeWithNullName() {
        val result = provider.maxSize(listOf(1, 2, 3), 2, null)
        assertEquals("'collection' must have 2 elements at most, but it has 3.", result)
    }

    @Test
    fun maxSizeWithLargerCollection() {
        val result = provider.maxSize(listOf(1, 2, 3, 4, 5, 6, 7), 5, "numbers")
        assertEquals("'numbers' must have 5 elements at most, but it has 7.", result)
    }

    @Test
    fun minSizeWithCustomName() {
        val result = provider.minSize(listOf("a", "b"), 3, "items")
        assertEquals("'items' must have at least 3 elements, but it has 2.", result)
    }

    @Test
    fun minSizeWithNullName() {
        val result = provider.minSize(listOf(1), 5, null)
        assertEquals("'collection' must have at least 5 elements, but it has 1.", result)
    }

    @Test
    fun minSizeWithEmptyCollection() {
        val result = provider.minSize(emptyList<String>(), 1, "myList")
        assertEquals("'myList' must have at least 1 elements, but it has 0.", result)
    }

    @Test
    fun notContainsWithCustomName() {
        val result = provider.notContains(listOf("a", "b", "c"), "b", "items")
        assertEquals("'items' must not contain the element: 'b', but it does.", result)
    }

    @Test
    fun notContainsWithNullName() {
        val result = provider.notContains(listOf("x", "y"), "x", null)
        assertEquals("'collection' must not contain the element: 'x', but it does.", result)
    }

    @Test
    fun notContainsWithIntegerElement() {
        val result = provider.notContains(listOf(1, 2, 3), 2, "numbers")
        assertEquals("'numbers' must not contain the element: '2', but it does.", result)
    }

    @Test
    fun notEmptyWithCustomName() {
        val result = provider.notEmpty(emptyList<String>(), "items")
        assertEquals("'items' must not be empty, but it is.", result)
    }

    @Test
    fun notEmptyWithNullName() {
        val result = provider.notEmpty(emptyList<Int>(), null)
        assertEquals("'collection' must not be empty, but it is.", result)
    }

    @Test
    fun notOfSizeWithCustomName() {
        val result = provider.notOfSize(listOf("a", "b", "c"), 3, "items")
        assertEquals("'items' must not have exactly 3 elements, but it does.", result)
    }

    @Test
    fun notOfSizeWithNullName() {
        val result = provider.notOfSize(listOf(1, 2), 2, null)
        assertEquals("'collection' must not have exactly 2 elements, but it does.", result)
    }

    @Test
    fun ofSizeWithCustomName() {
        val result = provider.ofSize(listOf("a", "b"), 3, "items")
        assertEquals("'items' must have exactly 3 elements, but it has 2.", result)
    }

    @Test
    fun ofSizeWithNullName() {
        val result = provider.ofSize(listOf(1, 2, 3, 4), 2, null)
        assertEquals("'collection' must have exactly 2 elements, but it has 4.", result)
    }

    @Test
    fun ofSizeWithEmptyCollection() {
        val result = provider.ofSize(emptyList<String>(), 1, "myList")
        assertEquals("'myList' must have exactly 1 elements, but it has 0.", result)
    }

    @Test
    fun sizeBetweenWithCustomName() {
        val result = provider.sizeBetween(listOf("a"), 2..5, "items")
        assertEquals("'items' size must be between 2 and 5 (inclusive), but it is 1.", result)
    }

    @Test
    fun sizeBetweenWithNullName() {
        val result = provider.sizeBetween(listOf(1, 2, 3, 4, 5, 6), 1..3, null)
        assertEquals("'collection' size must be between 1 and 3 (inclusive), but it is 6.", result)
    }

    @Test
    fun sizeBetweenBelowRange() {
        val result = provider.sizeBetween(listOf("a"), 3..5, "myList")
        assertEquals("'myList' size must be between 3 and 5 (inclusive), but it is 1.", result)
    }

    @Test
    fun sizeBetweenAboveRange() {
        val result = provider.sizeBetween(listOf(1, 2, 3, 4, 5, 6), 2..4, "numbers")
        assertEquals("'numbers' size must be between 2 and 4 (inclusive), but it is 6.", result)
    }

    @Test
    fun sizeNotBetweenWithCustomName() {
        val result = provider.sizeNotBetween(listOf("a", "b", "c"), 2..5, "items")
        assertEquals("'items' size must NOT be between 2 and 5 (inclusive), but it is 3.", result)
    }

    @Test
    fun sizeNotBetweenWithNullName() {
        val result = provider.sizeNotBetween(listOf(1, 2), 1..3, null)
        assertEquals("'collection' size must NOT be between 1 and 3 (inclusive), but it is 2.", result)
    }

    @Test
    fun sizeNotBetweenAtLowerBound() {
        val result = provider.sizeNotBetween(listOf("a", "b"), 2..5, "myList")
        assertEquals("'myList' size must NOT be between 2 and 5 (inclusive), but it is 2.", result)
    }

    @Test
    fun sizeNotBetweenAtUpperBound() {
        val result = provider.sizeNotBetween(listOf(1, 2, 3, 4, 5), 2..5, "numbers")
        assertEquals("'numbers' size must NOT be between 2 and 5 (inclusive), but it is 5.", result)
    }
}
