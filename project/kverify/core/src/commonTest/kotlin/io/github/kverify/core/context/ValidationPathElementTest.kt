package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ValidationPathElementTest {
    @Test
    fun propertyStoresName() {
        val property = PropertyPathElement("username")
        assertEquals("username", property.name)
    }

    @Test
    fun indexStoresIndex() {
        val index = IndexPathElement(3)
        assertEquals(3, index.index)
    }

    @Test
    fun propertyEquality() {
        val a = PropertyPathElement("name")
        val b = PropertyPathElement("name")
        val c = PropertyPathElement("other")

        assertEquals(a, b)
        assertNotEquals(a, c)
    }

    @Test
    fun indexEquality() {
        val a = IndexPathElement(0)
        val b = IndexPathElement(0)
        val c = IndexPathElement(1)

        assertEquals(a, b)
        assertNotEquals(a, c)
    }

    @Test
    fun propertyAndIndexAreNotEqual() {
        val property = PropertyPathElement("0")
        val index = IndexPathElement(0)

        assertNotEquals<ValidationPathElement>(property, index)
    }
}
