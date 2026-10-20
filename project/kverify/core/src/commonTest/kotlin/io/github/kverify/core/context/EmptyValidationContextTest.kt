package io.github.kverify.core.context

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.core.util.testValidationContexts
import io.github.kverify.core.util.testValidationElements
import io.github.kverify.core.util.testValidationKeys
import io.github.kverify.test.kit.util.repeatTest
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertSame

private const val ITERATOR_ATTEMPTS = 100

val EmptyValidationContextTest by testSuite {
    test("plus returns 'other'") {
        (testValidationElements.asList() + testValidationContexts).forEach { other ->
            assertSame(other, EmptyValidationContext + other)
        }
    }

    testValidationKeys.forEach { key ->
        testSuite("get and minusKey for $key") {
            test("get($key) returns null") {
                assertNull(EmptyValidationContext[key])
            }

            test("minusKey($key) returns itself") {
                assertSame(EmptyValidationContext, EmptyValidationContext.minusKey(key))
            }
        }
    }

    repeatTest(ITERATOR_ATTEMPTS, "iterator has no next element") {
        assertFalse {
            EmptyValidationContext.iterator().hasNext()
        }
    }

    repeatTest(ITERATOR_ATTEMPTS, "iterator.next always throws") {
        assertFailsWith<NoSuchElementException> {
            EmptyValidationContext.iterator().next()
        }
    }
}
