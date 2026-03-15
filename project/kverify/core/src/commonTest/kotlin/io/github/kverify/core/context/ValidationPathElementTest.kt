package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class ValidationPathElementTest {
    @Test
    fun namePathElementStoresName() {
        val name = "username"

        val element = NamePathElement(name)

        assertEquals(name, element.name)
    }

    @Test
    fun namePathElementsWithSameNameAreEqual() {
        val name = "email"

        val first = NamePathElement(name)
        val second = NamePathElement(name)

        assertEquals(first, second)
    }

    @Test
    fun namePathElementsWithDifferentNamesAreNotEqual() {
        val first = NamePathElement("firstName")
        val second = NamePathElement("lastName")

        assertNotEquals(first, second)
    }

    @Test
    fun namePathElementsWithSameNameHaveSameHashCode() {
        val name = "address"

        val first = NamePathElement(name)
        val second = NamePathElement(name)

        assertEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun namePathElementToStringContainsName() {
        val name = "city"

        val result = NamePathElement(name).toString()

        assertEquals("NamePathElement(name=$name)", result)
    }

    @Test
    fun namePathElementIteratorYieldsExactlyItself() {
        val element = NamePathElement("street")

        val elements = element.toList()

        assertEquals(listOf(element), elements)
    }

    @Test
    fun indexPathElementStoresIndex() {
        val index = 3

        val element = IndexPathElement(index)

        assertEquals(index, element.index)
    }

    @Test
    fun indexPathElementsWithSameIndexAreEqual() {
        val index = 7

        val first = IndexPathElement(index)
        val second = IndexPathElement(index)

        assertEquals(first, second)
    }

    @Test
    fun indexPathElementsWithDifferentIndicesAreNotEqual() {
        val first = IndexPathElement(1)
        val second = IndexPathElement(2)

        assertNotEquals(first, second)
    }

    @Test
    fun indexPathElementsWithSameIndexHaveSameHashCode() {
        val index = 4

        val first = IndexPathElement(index)
        val second = IndexPathElement(index)

        assertEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun indexPathElementToStringContainsIndex() {
        val index = 2

        val result = IndexPathElement(index).toString()

        assertEquals("IndexPathElement(index=$index)", result)
    }

    @Test
    fun indexPathElementIteratorYieldsExactlyItself() {
        val element = IndexPathElement(0)

        val elements = element.toList()

        assertEquals(listOf(element), elements)
    }
}
