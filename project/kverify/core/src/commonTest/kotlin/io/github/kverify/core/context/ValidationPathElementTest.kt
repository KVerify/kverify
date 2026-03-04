package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ValidationPathElementTest {
    @Test
    fun propertyStoresName() {
        val name = ValidationPathElement.Name("username")
        assertEquals("username", name.name)
    }

    @Test
    fun indexStoresIndex() {
        val index = ValidationPathElement.Index(3)
        assertEquals(3, index.index)
    }

    @Test
    fun propertyEquality() {
        val a = ValidationPathElement.Name("name")
        val b = ValidationPathElement.Name("name")
        val c = ValidationPathElement.Name("other")

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
        val name = ValidationPathElement.Name("0")
        val index = ValidationPathElement.Index(0)

        assertNotEquals<ValidationPathElement>(name, index)
    }
}
