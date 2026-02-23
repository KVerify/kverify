package io.github.kverify.context

import kotlin.test.Test
import kotlin.test.assertEquals

class ValidationContextTest {
    @Test
    fun pathElementsFiltersOnlyPathElements() {
        val property = ValidationPathElement.Property("name")
        val index = ValidationPathElement.Index(0)
        val customElement = object : ValidationContext.Element {}

        val context =
            ListValidationContext(
                listOf(property, customElement, index),
            )

        assertEquals(listOf(property, index), context.pathElements())
    }

    @Test
    fun pathElementsReturnsEmptyForNoPathElements() {
        val customElement = object : ValidationContext.Element {}
        val context = ListValidationContext(listOf(customElement))

        assertEquals(emptyList(), context.pathElements())
    }

    @Test
    fun pathElementsReturnsEmptyForEmptyContext() {
        assertEquals(emptyList(), EmptyValidationContext.pathElements())
    }
}
