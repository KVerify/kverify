package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame

class EmptyValidationContextTest {
    @Test
    fun iteratorYieldsNoElements() {
        val elements = EmptyValidationContext.toList()

        assertEquals(emptyList(), elements)
    }

    @Test
    fun iteratorHasNoNextOnFreshIterator() {
        val iterator = EmptyValidationContext.iterator()

        assertFalse(iterator.hasNext())
    }

    @Test
    fun plusReturnsOtherContext() {
        val result = EmptyValidationContext + CustomElement

        assertSame(CustomElement, result)
    }
}
