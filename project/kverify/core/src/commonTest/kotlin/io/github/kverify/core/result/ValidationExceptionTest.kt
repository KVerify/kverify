package io.github.kverify.core.result

import io.github.kverify.core.rule.ViolationReason
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ValidationExceptionTest {
    private val violation1 = ViolationReason("error1")
    private val violation2 = ViolationReason("error2")

    @Test
    fun constructorWithMessageAndViolations() {
        val exception =
            ValidationException(
                message = "custom message",
                violations = listOf(violation1),
            )

        assertEquals("custom message", exception.message)
        assertEquals(listOf(violation1), exception.violations)
    }

    @Test
    fun constructorWithViolationListAutoGeneratesMessage() {
        val exception =
            ValidationException(
                violations = listOf(violation1, violation2),
            )

        assertTrue(exception.message!!.contains("error1"))
        assertTrue(exception.message!!.contains("error2"))
        assertEquals(listOf(violation1, violation2), exception.violations)
    }

    @Test
    fun constructorWithEmptyViolationListAutoGeneratesMessage() {
        val exception =
            ValidationException(
                violations = emptyList(),
            )

        assertEquals("Validation failed", exception.message)
    }

    @Test
    fun constructorWithSingleViolation() {
        val exception = ValidationException(violation1)

        assertEquals(listOf(violation1), exception.violations)
    }

    @Test
    fun constructorWithVarargViolations() {
        val exception = ValidationException(violation1, violation2)

        assertEquals(listOf(violation1, violation2), exception.violations)
    }

    @Test
    fun constructorWithCause() {
        val cause = RuntimeException("root cause")
        val exception =
            ValidationException(
                violations = listOf(violation1),
                cause = cause,
            )

        assertEquals(cause, exception.cause)
    }

    @Test
    fun defaultViolationsIsEmpty() {
        val exception = ValidationException(message = "error")

        assertTrue(exception.violations.isEmpty())
    }

    @Test
    fun isThrowable() {
        val exception = ValidationException(message = "error")

        assertIs<Throwable>(exception)
    }

    // ThrowingValidationScopeException

    @Test
    fun throwingScopeExceptionStoresViolation() {
        val exception = ThrowingValidationScopeException(violation1)

        assertEquals(violation1, exception.violation)
        assertEquals(listOf(violation1), exception.violations)
    }

    @Test
    fun throwingScopeExceptionAutoGeneratesMessage() {
        val exception = ThrowingValidationScopeException(violation1)

        assertTrue(exception.message!!.contains("error1"))
    }

    @Test
    fun throwingScopeExceptionWithCustomMessage() {
        val exception =
            ThrowingValidationScopeException(
                message = "custom",
                violation = violation1,
            )

        assertEquals("custom", exception.message)
        assertEquals(violation1, exception.violation)
    }

    @Test
    fun throwingScopeExceptionIsValidationException() {
        val exception = ThrowingValidationScopeException(violation1)

        assertIs<ValidationException>(exception)
    }
}
