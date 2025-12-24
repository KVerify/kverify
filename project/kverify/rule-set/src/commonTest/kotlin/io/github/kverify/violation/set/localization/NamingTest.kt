package io.github.kverify.violation.set.localization

import kotlin.test.Test
import kotlin.test.assertEquals

class NamingTest {
    @Test
    fun resolveCollectionNameWithProvidedName() {
        val result = resolveCollectionName("myList")
        assertEquals("'myList'", result)
    }

    @Test
    fun resolveCollectionNameWithNullUsesDefault() {
        val result = resolveCollectionName(null)
        assertEquals("'collection'", result)
    }

    @Test
    fun resolveComparableNameWithProvidedName() {
        val result = resolveComparableName("age")
        assertEquals("'age'", result)
    }

    @Test
    fun resolveComparableNameWithNullUsesDefault() {
        val result = resolveComparableName(null)
        assertEquals("'comparable'", result)
    }

    @Test
    fun resolveStringNameWithProvidedName() {
        val result = resolveStringName("username")
        assertEquals("'username'", result)
    }

    @Test
    fun resolveStringNameWithNullUsesDefault() {
        val result = resolveStringName(null)
        assertEquals("'string'", result)
    }

    @Test
    fun joinWithLimitAndBracketsForFewItems() {
        val result = listOf("a", "b", "c").joinWithLimitAndBrackets()
        assertEquals("[a, b, c]", result)
    }

    @Test
    fun joinWithLimitAndBracketsForSingleItem() {
        val result = listOf("a").joinWithLimitAndBrackets()
        assertEquals("[a]", result)
    }

    @Test
    fun joinWithLimitAndBracketsForEmptyList() {
        val result = emptyList<String>().joinWithLimitAndBrackets()
        assertEquals("[]", result)
    }

    @Test
    fun joinWithLimitAndBracketsLimitsToFiveItems() {
        val result = listOf("a", "b", "c", "d", "e", "f", "g").joinWithLimitAndBrackets()
        assertEquals("[a, b, c, d, e, ...]", result)
    }

    @Test
    fun joinWithLimitAndBracketsExactlyFiveItems() {
        val result = listOf("a", "b", "c", "d", "e").joinWithLimitAndBrackets()
        assertEquals("[a, b, c, d, e]", result)
    }

    @Test
    fun joinWithLimitAndBracketsWorksWithIntegers() {
        val result = listOf(1, 2, 3).joinWithLimitAndBrackets()
        assertEquals("[1, 2, 3]", result)
    }

    @Test
    fun joinWithLimitAndBracketsWorksWithChars() {
        val result = listOf('a', 'b', 'c').joinWithLimitAndBrackets()
        assertEquals("[a, b, c]", result)
    }

    @Test
    fun joinWithLimitAndBracketsWorksWithMixedTypes() {
        val result = listOf<Any>("text", 123, 'x').joinWithLimitAndBrackets()
        assertEquals("[text, 123, x]", result)
    }
}
