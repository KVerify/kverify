package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class EmptyValidationContextTest {
    @Test
    fun fold_returnsInitial() {
        val initial = "unchanged"

        val result = EmptyValidationContext.fold(initial) { _, _ -> "should not be called" }

        assertSame(initial, result)
    }

    @Test
    fun plusOther_returnsOther() {
        val other = NamePathElement("field")

        assertSame(other, EmptyValidationContext + other)
    }

    @Test
    fun otherPlusEmpty_returnsSelf() {
        val element = NamePathElement("field")

        assertSame(element, element + EmptyValidationContext)
    }
}
