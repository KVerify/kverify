package io.github.kverify.scope

import io.github.kverify.context.EmptyValidationContext
import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.result.ThrowingValidationScopeException
import io.github.kverify.rule.ViolationReason
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertSame

class ThrowingValidationScopeTest {
    @Test
    fun defaultContextIsEmpty() {
        val scope = ThrowingValidationScope()

        assertSame(EmptyValidationContext, scope.validationContext)
    }

    @Test
    fun customContextIsPreserved() {
        val context = ListValidationContext(ValidationPathElement.Property("user"))
        val scope = ThrowingValidationScope(context)

        assertSame(context, scope.validationContext)
    }

    @Test
    fun onFailureThrowsException() {
        val scope = ThrowingValidationScope()
        val violation = ViolationReason("error")

        val exception =
            assertFailsWith<ThrowingValidationScopeException> {
                scope.onFailure(violation)
            }

        assertEquals(violation, exception.violation)
    }

    @Test
    fun verifyWithThrowingReturnsValueOnSuccess() {
        val result =
            verifyWithThrowing {
                failIf(false) { ViolationReason("should not throw") }
                "success"
            }

        assertEquals("success", result)
    }

    @Test
    fun verifyWithThrowingThrowsOnFirstFailure() {
        assertFailsWith<ThrowingValidationScopeException> {
            verifyWithThrowing {
                failIf(true) { ViolationReason("error") }
            }
        }
    }

    @Test
    fun verifyWithThrowingStopsAtFirstFailure() {
        var secondReached = false

        assertFailsWith<ThrowingValidationScopeException> {
            verifyWithThrowing {
                failIf(true) { ViolationReason("first") }
                secondReached = true
            }
        }

        assertEquals(false, secondReached)
    }

    @Test
    fun verifyWithFailFastReturnsNullOnSuccess() {
        val result =
            verifyWithFailFast {
                failIf(false) { ViolationReason("should not fail") }
            }

        assertNull(result)
    }

    @Test
    fun verifyWithFailFastReturnsFirstViolation() {
        val result =
            verifyWithFailFast {
                failIf(true) { ViolationReason("error") }
            }

        assertIs<ViolationReason>(result)
        assertEquals("error", result.reason)
    }

    @Test
    fun verifyWithFailFastStopsAtFirstFailure() {
        var secondReached = false

        val result =
            verifyWithFailFast {
                failIf(true) { ViolationReason("first") }
                secondReached = true
            }

        assertEquals("first", result?.reason)
        assertEquals(false, secondReached)
    }

    @Test
    fun verifyWithThrowingAcceptsCustomContext() {
        val context = ListValidationContext(ValidationPathElement.Property("user"))

        val result =
            verifyWithThrowing(context) {
                assertEquals(
                    listOf(ValidationPathElement.Property("user")),
                    validationContext.elements,
                )
                "done"
            }

        assertEquals("done", result)
    }
}
