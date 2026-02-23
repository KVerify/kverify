package io.github.kverify.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertSame

class EmptyValidationContextTest {
    @Test
    fun elementsIsEmptyList() {
        assertEquals(emptyList(), EmptyValidationContext.elements)
    }

    @Test
    fun plusElementReturnsListValidationContext() {
        val element = ValidationPathElement.Property("name")
        val result = EmptyValidationContext + element

        assertIs<ListValidationContext>(result)
        assertEquals(listOf(element), result.elements)
    }

    @Test
    fun plusEmptyContextReturnsSameContext() {
        val result = EmptyValidationContext + EmptyValidationContext

        assertSame(EmptyValidationContext, result)
    }

    @Test
    fun plusListContextReturnsOtherContext() {
        val other = ListValidationContext(ValidationPathElement.Property("name"))
        val result = EmptyValidationContext + other

        assertSame(other, result)
    }
}
