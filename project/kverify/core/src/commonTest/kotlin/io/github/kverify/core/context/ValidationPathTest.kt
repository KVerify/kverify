package io.github.kverify.core.context

import kotlin.test.Test
import kotlin.test.assertEquals

class ValidationPathTest {
    @Test
    fun mixedElements_preservesOrder() {
        val first = NamePathElement("a")
        val second = IndexPathElement(1)
        val third = NamePathElement("c")

        val ctx = first + second + third

        assertEquals(listOf(first, second, third), ctx.validationPath())
    }

    @Test
    fun nonPathElement_excludedFromValidationPath() {
        val first = NamePathElement("a")
        val third = NamePathElement("c")

        val ctx = first + CustomValidationContextElement + third

        assertEquals(listOf(first, third), ctx.validationPath())
    }

    @Test
    fun emptyPlusElement_returnsElementPath() {
        val element = NamePathElement("field")

        val ctx = EmptyValidationContext + element

        assertEquals(element, ctx.validationPath().single())
    }

    @Test
    fun elementPlusEmpty_returnsElementPath() {
        val element = NamePathElement("field")

        val ctx = element + EmptyValidationContext

        assertEquals(element, ctx.validationPath().single())
    }
}

private object CustomValidationContextElement : ValidationContext.Element
