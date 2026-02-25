package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ListValidationContextTest {
    @Test
    fun constructorWithList() {
        val elements =
            listOf(
                ValidationPathElement.Property("name"),
                ValidationPathElement.Index(0),
            )
        val context = ListValidationContext(elements)

        assertEquals(elements, context.elements)
    }

    @Test
    fun factoryWithSingleElement() {
        val element = ValidationPathElement.Property("name")
        val context = ListValidationContext(element)

        assertEquals(listOf(element), context.elements)
    }

    @Test
    fun factoryWithVarargElements() {
        val property = ValidationPathElement.Property("users")
        val index = ValidationPathElement.Index(0)
        val context = ListValidationContext(property, index)

        assertEquals(listOf(property, index), context.elements)
    }

    @Test
    fun plusElementAppendsToElements() {
        val property = ValidationPathElement.Property("name")
        val index = ValidationPathElement.Index(0)
        val context = ListValidationContext(property)

        val result = context + index

        assertIs<ListValidationContext>(result)
        assertEquals(listOf(property, index), result.elements)
    }

    @Test
    fun plusContextCombinesElements() {
        val context1 = ListValidationContext(ValidationPathElement.Property("user"))
        val context2 = ListValidationContext(ValidationPathElement.Property("name"))

        val result = context1 + context2

        assertIs<ListValidationContext>(result)
        assertEquals(
            listOf(
                ValidationPathElement.Property("user"),
                ValidationPathElement.Property("name"),
            ),
            result.elements,
        )
    }

    @Test
    fun dataClassEquality() {
        val context1 = ListValidationContext(ValidationPathElement.Property("name"))
        val context2 = ListValidationContext(ValidationPathElement.Property("name"))

        assertEquals(context1, context2)
    }
}
