package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
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

    @Test
    fun lastOfTypeOrNullReturnsNullWhenNoMatchingElementExists() {
        val context = NamePathElement("a") + IndexPathElement(0)

        val result = context.lastOfTypeOrNull<CustomElement>()

        assertNull(result)
    }

    @Test
    fun lastOfTypeOrNullReturnsSingleMatchingElement() {
        val element = CustomElement(1)

        val result = element.lastOfTypeOrNull<CustomElement>()

        assertEquals(element, result)
    }

    @Test
    fun lastOfTypeOrNullReturnsLastMatchingElement() {
        val first = CustomElement(1)
        val last = CustomElement(2)
        val context = first + NamePathElement("between") + last

        val result = context.lastOfTypeOrNull<CustomElement>()

        assertEquals(last, result)
    }

    @Test
    fun lastOfTypeOrNullSkipsNonMatchingElements() {
        val target = CustomElement(1)
        val context = NamePathElement("a") + target + IndexPathElement(0)

        val result = context.lastOfTypeOrNull<CustomElement>()

        assertEquals(target, result)
    }
}
