package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotEquals

class ValidationPathElementTest {
    @Test
    fun propertyStoresName() {
        val property = ValidationPathElement.Property("username")

        assertEquals("username", property.name)
    }

    @Test
    fun indexStoresIndex() {
        val index = ValidationPathElement.Index(5)

        assertEquals(5, index.index)
    }

    @Test
    fun propertyIsValidationContextElement() {
        val property = ValidationPathElement.Property("name")

        assertIs<ValidationContext.Element>(property)
        assertIs<ValidationPathElement>(property)
    }

    @Test
    fun indexIsValidationContextElement() {
        val index = ValidationPathElement.Index(0)

        assertIs<ValidationContext.Element>(index)
        assertIs<ValidationPathElement>(index)
    }

    @Test
    fun propertyDataClassEquality() {
        val property1 = ValidationPathElement.Property("name")
        val property2 = ValidationPathElement.Property("name")
        val property3 = ValidationPathElement.Property("other")

        assertEquals(property1, property2)
        assertNotEquals(property1, property3)
    }

    @Test
    fun indexDataClassEquality() {
        val index1 = ValidationPathElement.Index(0)
        val index2 = ValidationPathElement.Index(0)
        val index3 = ValidationPathElement.Index(1)

        assertEquals(index1, index2)
        assertNotEquals(index1, index3)
    }
}
