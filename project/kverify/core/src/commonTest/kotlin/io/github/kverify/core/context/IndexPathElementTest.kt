package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class IndexPathElementTest {
    @Test
    fun equalWhenSameIndex() {
        val index = 3

        assertEquals(IndexPathElement(index), IndexPathElement(index))
    }

    @Test
    fun notEqualWhenDifferentIndex() {
        assertNotEquals(IndexPathElement(0), IndexPathElement(1))
    }

    @Test
    fun hashCode_consistentWithEquals() {
        val index = 7

        assertEquals(IndexPathElement(index).hashCode(), IndexPathElement(index).hashCode())
        assertNotEquals(IndexPathElement(0).hashCode(), IndexPathElement(1).hashCode())
    }

    @Test
    fun validationPath_containsSelf() {
        val element = IndexPathElement(5)

        assertEquals(element, element.validationPath().single())
    }

    @Test
    fun fold_callsOperationWithSelf() {
        val element = IndexPathElement(2)

        val result = element.fold(emptyList<ValidationContext.Element>()) { acc, el -> acc + el }

        assertEquals(element, result.single())
    }
}
