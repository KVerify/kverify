package io.github.kverify.core.model

import io.github.kverify.core.context.IndexPathElement
import io.github.kverify.core.context.NamePathElement
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ValidationPathTest {
    @Test
    fun storesElements() {
        val elements = listOf(NamePathElement("a"), IndexPathElement(0))

        val path = ValidationPath(elements)

        assertEquals(elements, path.elements)
    }

    @Test
    fun twoPathsWithSameElementsAreEqual() {
        val elements = listOf(NamePathElement("a"), IndexPathElement(0))

        assertEquals(ValidationPath(elements), ValidationPath(elements))
    }

    @Test
    fun twoPathsWithDifferentElementsAreNotEqual() {
        assertNotEquals(
            ValidationPath(listOf(NamePathElement("a"))),
            ValidationPath(listOf(NamePathElement("b"))),
        )
    }

    @Test
    fun twoPathsWithSameElementsHaveSameHashCode() {
        val elements = listOf(NamePathElement("a"), IndexPathElement(0))

        assertEquals(ValidationPath(elements).hashCode(), ValidationPath(elements).hashCode())
    }

    @Test
    fun toStringOnEmptyPathReturnsEmptyWrapper() {
        val result = ValidationPath(emptyList()).toString()

        assertEquals("ValidationPath()", result)
    }

    @Test
    fun toStringOnSingleNameElementQuotesName() {
        val result = ValidationPath(listOf(NamePathElement("user"))).toString()

        assertEquals("ValidationPath(\"user\")", result)
    }

    @Test
    fun toStringOnSingleIndexElementUsesBareLiteral() {
        val result = ValidationPath(listOf(IndexPathElement(2))).toString()

        assertEquals("ValidationPath(2)", result)
    }

    @Test
    fun toStringOnMixedElementsFormatsCorrectly() {
        val elements = listOf(NamePathElement("users"), IndexPathElement(0), NamePathElement("address"))

        val result = ValidationPath(elements).toString()

        assertEquals("ValidationPath(\"users\", 0, \"address\")", result)
    }
}
