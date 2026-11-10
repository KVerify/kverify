package io.github.kverify.core.context

import de.infix.testBalloon.framework.core.testSuite
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertSame

val EmptyValidationContextTest by testSuite {
    testSuite("plus") {
        test("returns the other context") {
            val other = ProbeElement()

            val result = EmptyValidationContext + other

            assertSame(other, result)
        }
    }

    testSuite("get") {
        test("returns null") {
            val result = EmptyValidationContext[ProbeKey]

            assertNull(result)
        }
    }

    testSuite("minusKey") {
        test("returns EmptyValidationContext") {
            val result = EmptyValidationContext.minusKey(ProbeKey)

            assertSame(EmptyValidationContext, result)
        }
    }

    testSuite("iterator") {
        test("has no elements") {
            val iterator = EmptyValidationContext.iterator()

            assertFalse(iterator.hasNext())
        }

        test("next throws NoSuchElementException") {
            val iterator = EmptyValidationContext.iterator()

            assertFailsWith<NoSuchElementException> { iterator.next() }
        }
    }
}

private class ProbeElement : ValidationContext.Element {
    override val key: ValidationContext.Key<*> = ProbeKey
}

private object ProbeKey : ValidationContext.Key<ProbeElement> {
    override fun onCollision(
        oldElement: ValidationContext.Element,
        newElement: ValidationContext.Element,
    ): ValidationContext.Element = newElement
}
