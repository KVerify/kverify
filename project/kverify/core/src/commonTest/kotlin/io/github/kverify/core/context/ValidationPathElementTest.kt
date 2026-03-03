package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ValidationPathElementTest {
    @Test
    fun propertyStoresName() {
        val property = ValidationPathElement.Property("username")
        assertEquals("username", property.name)
    }

    @Test
    fun indexStoresIndex() {
        val index = ValidationPathElement.Index(3)
        assertEquals(3, index.index)
    }

    @Test
    fun propertyEquality() {
        val a = ValidationPathElement.Property("name")
        val b = ValidationPathElement.Property("name")
        val c = ValidationPathElement.Property("other")

        assertEquals(a, b)
        assertNotEquals(a, c)
    }

    @Test
    fun indexEquality() {
        val a = ValidationPathElement.Index(0)
        val b = ValidationPathElement.Index(0)
        val c = ValidationPathElement.Index(1)

        assertEquals(a, b)
        assertNotEquals(a, c)
    }

    @Test
    fun propertyAndIndexAreNotEqual() {
        val property = ValidationPathElement.Property("0")
        val index = ValidationPathElement.Index(0)

        assertNotEquals<ValidationPathElement>(property, index)
    }
}
