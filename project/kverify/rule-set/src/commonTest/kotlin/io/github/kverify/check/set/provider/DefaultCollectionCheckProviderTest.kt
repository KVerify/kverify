package io.github.kverify.check.set.provider

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DefaultCollectionCheckProviderTest {
    private val provider = DefaultCollectionCheckProvider()

    @Test
    fun containsReturnsTrueWhenElementPresent() {
        val check = provider.contains("test")
        assertTrue(check.isValid(listOf("test", "other")))
    }

    @Test
    fun containsReturnsFalseWhenElementAbsent() {
        val check = provider.contains("missing")
        assertFalse(check.isValid(listOf("test", "other")))
    }

    @Test
    fun containsReturnsFalseForEmptyCollection() {
        val check = provider.contains("test")
        assertFalse(check.isValid(emptyList()))
    }

    @Test
    fun containsAllReturnsTrueWhenAllElementsPresent() {
        val check = provider.containsAll(listOf("a", "b"))
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun containsAllReturnsFalseWhenSomeElementsMissing() {
        val check = provider.containsAll(listOf("a", "d"))
        assertFalse(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun containsAllReturnsTrueForEmptyElements() {
        val check = provider.containsAll<String, List<String>>(emptyList())
        assertTrue(check.isValid(listOf("a", "b")))
    }

    @Test
    fun containsAllReturnsFalseWhenCollectionEmptyButElementsNot() {
        val check = provider.containsAll(listOf("a"))
        assertFalse(check.isValid(emptyList()))
    }

    @Test
    fun containsNoneReturnsTrueWhenNoElementsPresent() {
        val check = provider.containsNone(listOf("d", "e"))
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun containsNoneReturnsFalseWhenAnyElementPresent() {
        val check = provider.containsNone(listOf("b", "d"))
        assertFalse(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun containsNoneReturnsTrueForEmptyCollection() {
        val check = provider.containsNone(listOf("a"))
        assertTrue(check.isValid(emptyList()))
    }

    @Test
    fun containsNoneReturnsTrueForEmptyElements() {
        val check = provider.containsNone<String, List<String>>(emptyList())
        assertTrue(check.isValid(listOf("a", "b")))
    }

    @Test
    fun containsOnlyReturnsTrueWhenAllElementsAreAllowed() {
        val check = provider.containsOnly(listOf("a", "b", "c", "d"))
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun containsOnlyReturnsFalseWhenDisallowedElementPresent() {
        val check = provider.containsOnly(listOf("a", "b"))
        assertFalse(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun containsOnlyReturnsTrueForEmptyCollection() {
        val check = provider.containsOnly(listOf("a"))
        assertTrue(check.isValid(emptyList()))
    }

    @Test
    fun containsOnlyReturnsTrueForEmptyElements() {
        val check = provider.containsOnly<String, List<String>>(emptyList())
        assertTrue(check.isValid(listOf("a", "b")))
    }

    @Test
    fun notContainsReturnsTrueWhenElementAbsent() {
        val check = provider.notContains("missing")
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun notContainsReturnsFalseWhenElementPresent() {
        val check = provider.notContains("b")
        assertFalse(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun notContainsReturnsTrueForEmptyCollection() {
        val check = provider.notContains("test")
        assertTrue(check.isValid(emptyList()))
    }

    @Test
    fun notEmptyReturnsTrueForNonEmptyCollection() {
        val check = provider.notEmpty()
        assertTrue(check.isValid(listOf("a")))
    }

    @Test
    fun notEmptyReturnsFalseForEmptyCollection() {
        val check = provider.notEmpty()
        assertFalse(check.isValid(emptyList<Any>()))
    }

    @Test
    fun distinctReturnsTrueForDistinctElements() {
        val check = provider.distinct()
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun distinctReturnsFalseForDuplicateElements() {
        val check = provider.distinct()
        assertFalse(check.isValid(listOf("a", "b", "a")))
    }

    @Test
    fun distinctReturnsTrueForEmptyCollection() {
        val check = provider.distinct()
        assertTrue(check.isValid(emptyList<Any>()))
    }

    @Test
    fun distinctReturnsTrueForSingleElement() {
        val check = provider.distinct()
        assertTrue(check.isValid(listOf("a")))
    }

    @Test
    fun ofSizeReturnsTrueWhenSizeMatches() {
        val check = provider.ofSize(3)
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun ofSizeReturnsFalseWhenSizeDiffers() {
        val check = provider.ofSize(2)
        assertFalse(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun ofSizeReturnsTrueForEmptyCollectionWithZeroSize() {
        val check = provider.ofSize(0)
        assertTrue(check.isValid(emptyList<Any>()))
    }

    @Test
    fun notOfSizeReturnsTrueWhenSizeDiffers() {
        val check = provider.notOfSize(2)
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun notOfSizeReturnsFalseWhenSizeMatches() {
        val check = provider.notOfSize(3)
        assertFalse(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun minSizeReturnsTrueWhenSizeEqualsMinimum() {
        val check = provider.minSize(3)
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun minSizeReturnsFalseWhenSizeBelowMinimum() {
        val check = provider.minSize(5)
        assertFalse(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun minSizeReturnsTrueWhenSizeAboveMinimum() {
        val check = provider.minSize(2)
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun maxSizeReturnsTrueWhenSizeEqualsMaximum() {
        val check = provider.maxSize(3)
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun maxSizeReturnsTrueWhenSizeBelowMaximum() {
        val check = provider.maxSize(5)
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun maxSizeReturnsFalseWhenSizeAboveMaximum() {
        val check = provider.maxSize(2)
        assertFalse(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun sizeBetweenReturnsTrueWhenSizeInRange() {
        val check = provider.sizeBetween(2..5)
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun sizeBetweenReturnsTrueWhenSizeAtLowerBound() {
        val check = provider.sizeBetween(3..5)
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun sizeBetweenReturnsTrueWhenSizeAtUpperBound() {
        val check = provider.sizeBetween(1..3)
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun sizeBetweenReturnsFalseWhenSizeBelowRange() {
        val check = provider.sizeBetween(4..6)
        assertFalse(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun sizeBetweenReturnsFalseWhenSizeAboveRange() {
        val check = provider.sizeBetween(0..2)
        assertFalse(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun sizeNotBetweenReturnsTrueWhenSizeBelowRange() {
        val check = provider.sizeNotBetween(4..6)
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun sizeNotBetweenReturnsTrueWhenSizeAboveRange() {
        val check = provider.sizeNotBetween(0..2)
        assertTrue(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun sizeNotBetweenReturnsFalseWhenSizeInRange() {
        val check = provider.sizeNotBetween(2..5)
        assertFalse(check.isValid(listOf("a", "b", "c")))
    }

    @Test
    fun sizeNotBetweenReturnsFalseWhenSizeAtBounds() {
        val check = provider.sizeNotBetween(3..3)
        assertFalse(check.isValid(listOf("a", "b", "c")))
    }
}
