package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class NamePathElementTest {
    @Test
    fun equalWhenSameName() {
        val name = "field"

        assertEquals(NamePathElement(name), NamePathElement(name))
    }

    @Test
    fun notEqualWhenDifferentName() {
        assertNotEquals(NamePathElement("first"), NamePathElement("second"))
    }

    @Test
    fun hashCode_consistentWithEquals() {
        val name = "field"

        assertEquals(NamePathElement(name).hashCode(), NamePathElement(name).hashCode())
        assertNotEquals(NamePathElement("first").hashCode(), NamePathElement("second").hashCode())
    }

    @Test
    fun validationPath_containsSelf() {
        val element = NamePathElement("field")

        assertEquals(element, element.validationPath().single())
    }

    @Test
    fun fold_callsOperationWithSelf() {
        val element = NamePathElement("field")

        val result = element.fold(emptyList<ValidationContext.Element>()) { acc, el -> acc + el }

        assertEquals(element, result.single())
    }
}
