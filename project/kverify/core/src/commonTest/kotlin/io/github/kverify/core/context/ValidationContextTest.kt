package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class ValidationContextTest {
    @Test
    fun elementIteratorYieldsExactlyItself() {
        val yielded = CustomElement.toList()

        assertEquals(listOf(CustomElement), yielded)
    }

    @Test
    fun elementAsValidationContextContainsOnlyItself() {
        val elements = CustomElement.toList()

        assertEquals(listOf(CustomElement), elements)
    }

    @Test
    fun plusEmptyOnRightReturnsSelf() {
        val result = CustomElement + EmptyValidationContext

        assertSame(CustomElement, result)
    }

    @Test
    fun plusTwoElementsPreservesLeftThenRightOrder() {
        val first = CustomElement(1)
        val second = CustomElement(2)
        val third = CustomElement(3)

        val combined = first + second + third

        assertEquals(listOf(first, second, third), combined.toList())
    }

    @Test
    fun validationPathOnEmptyContextReturnsEmptyList() {
        val path = EmptyValidationContext.validationPath().elements

        assertEquals(emptyList(), path)
    }

    @Test
    fun validationPathReturnsOnlyPathElements() {
        val name = NamePathElement("user")
        val index = IndexPathElement(1)

        val path = (name + index + CustomElement).validationPath().elements

        assertEquals(listOf(name, index), path)
    }

    @Test
    fun validationPathPreservesPathElementOrder() {
        val a = NamePathElement("a")
        val b = IndexPathElement(0)
        val c = NamePathElement("c")
        val d = IndexPathElement(1)

        val path = (a + b + c + d).validationPath().elements

        assertEquals(listOf(a, b, c, d), path)
    }
}
