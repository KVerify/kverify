package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CombinedContextTest {
    @Test
    fun iteratorYieldsLeftThenRight() {
        val left = CustomElement(1)
        val right = CustomElement(2)

        val elements = CombinedContext(left, right).toList()

        assertEquals(listOf(left, right), elements)
    }

    @Test
    fun twoLevelNestedContextPreservesOrder() {
        val first = CustomElement(1)
        val second = CustomElement(2)
        val third = CustomElement(3)

        val elements = CombinedContext(CombinedContext(first, second), third).toList()

        assertEquals(listOf(first, second, third), elements)
    }

    @Test
    fun deepChainDoesNotOverflow() {
        val elementCount = 10_000
        val element = CustomElement

        var context: ValidationContext = element
        for (i in 1..<elementCount) {
            context = CombinedContext(context, element)
        }

        var count = 0
        val iterator = context.iterator()
        while (iterator.hasNext()) {
            iterator.next()
            count++
        }

        assertEquals(elementCount, count)
    }
}
