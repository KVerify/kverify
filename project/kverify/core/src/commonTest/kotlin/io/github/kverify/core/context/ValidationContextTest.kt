package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class ValidationContextTest {
    @Test
    fun emptyContextFoldsToInitial() {
        val result = EmptyValidationContext.fold(0) { acc, _ -> acc + 1 }
        assertEquals(0, result)
    }

    @Test
    fun emptyPlusContextReturnsOther() {
        val element = ValidationPathElement.Name("name")
        val result = EmptyValidationContext + element
        assertSame(element, result)
    }

    @Test
    fun contextPlusEmptyReturnsSelf() {
        val element = ValidationPathElement.Name("name")
        val result = element + EmptyValidationContext
        assertSame(element, result)
    }

    @Test
    fun elementFoldsOverItself() {
        val element = ValidationPathElement.Name("name")
        val result =
            element.fold(mutableListOf<ValidationContext.Element>()) { acc, e ->
                acc.apply { add(e) }
            }
        assertEquals(1, result.size)
        assertSame(element, result[0])
    }

    @Test
    fun combinedContextFoldsInOrder() {
        val a = ValidationPathElement.Name("a")
        val b = ValidationPathElement.Index(0)
        val c = ValidationPathElement.Name("c")

        val combined = (a + b) + c

        val elements =
            combined.fold(mutableListOf<ValidationContext.Element>()) { acc, e ->
                acc.apply { add(e) }
            }

        assertEquals(3, elements.size)
        assertEquals(a, elements[0])
        assertEquals(b, elements[1])
        assertEquals(c, elements[2])
    }

    @Test
    fun validationPathExtractsPathElements() {
        val name = ValidationPathElement.Name("user")
        val index = ValidationPathElement.Index(2)

        val context = name + index
        val path = context.validationPath()

        assertEquals(2, path.size)
        assertEquals(name, path[0])
        assertEquals(index, path[1])
    }

    @Test
    fun validationPathFromEmptyContextIsEmpty() {
        val path = EmptyValidationContext.validationPath()
        assertTrue(path.isEmpty())
    }

    @Test
    fun validationPathFromSingleElement() {
        val name = ValidationPathElement.Name("name")
        val path = name.validationPath()

        assertEquals(1, path.size)
        assertEquals(name, path[0])
    }

    @Test
    fun multipleCombinationsPreserveOrder() {
        val elements = (0..4).map { ValidationPathElement.Index(it) }

        var context: ValidationContext = EmptyValidationContext
        for (element in elements) {
            context += element
        }

        val path = context.validationPath()
        assertEquals(5, path.size)
        elements.forEachIndexed { i, expected ->
            assertEquals(expected, path[i])
        }
    }
}
