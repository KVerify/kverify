package io.github.kverify.core.exception

import io.github.kverify.core.violation.ViolationReason
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ValidationExceptionTest {
    @Test
    fun constructorWithDefaultParameters() {
        val exception = ValidationException()

        assertNull(exception.message)
        assertEquals(emptyList(), exception.violations)
        assertNull(exception.cause)
    }

    @Test
    fun constructorWithMessageViolationsAndCause() {
        val message = "Custom message"
        val violations = listOf(violation("error1"), violation("error2"))
        val cause = RuntimeException("cause")

        val exception =
            ValidationException(
                message = message,
                violations = violations,
                cause = cause,
            )

        assertEquals(message, exception.message)
        assertEquals(violations, exception.violations)
        assertEquals(cause, exception.cause)
    }

    @Test
    fun constructorWithMessageAndViolations() {
        val message = "Custom message"
        val violations = listOf(violation("error"))

        val exception =
            ValidationException(
                message = message,
                violations = violations,
            )

        assertEquals(message, exception.message)
        assertEquals(violations, exception.violations)
        assertNull(exception.cause)
    }

    @Test
    fun constructorWithOnlyMessage() {
        val message = "Custom message"

        val exception = ValidationException(message = message)

        assertEquals(message, exception.message)
        assertTrue(exception.violations.isEmpty())
        assertNull(exception.cause)
    }

    @Test
    fun constructorWithEmptyViolations() {
        val exception =
            ValidationException(
                violations = emptyList(),
            )

        assertEquals("Validation failed", exception.message)
        assertTrue(exception.violations.isEmpty())
    }

    @Test
    fun constructorWithSingleViolation() {
        val violation = violation("error message")

        val exception =
            ValidationException(
                violations = listOf(violation),
            )

        assertEquals("Validation failed:\n\t- error message", exception.message)
        assertEquals(listOf(violation), exception.violations)
    }

    @Test
    fun constructorWithMultipleViolations() {
        val violation1 = violation("error 1")
        val violation2 = violation("error 2")
        val violation3 = violation("error 3")

        val exception =
            ValidationException(
                violations = listOf(violation1, violation2, violation3),
            )

        val expectedMessage = "Validation failed:\n\t- error 1\n\t- error 2\n\t- error 3"
        assertEquals(expectedMessage, exception.message)
        assertEquals(listOf(violation1, violation2, violation3), exception.violations)
    }

    @Test
    fun constructorWithViolationsAndCause() {
        val violations = listOf(violation("error"))
        val cause = IllegalStateException("underlying cause")

        val exception =
            ValidationException(
                violations = violations,
                cause = cause,
            )

        assertEquals("Validation failed:\n\t- error", exception.message)
        assertEquals(violations, exception.violations)
        assertEquals(cause, exception.cause)
    }

    @Test
    fun constructorWithVarargViolations() {
        val violation1 = violation("error 1")
        val violation2 = violation("error 2")

        val exception = ValidationException(violation1, violation2)

        assertEquals("Validation failed:\n\t- error 1\n\t- error 2", exception.message)
        assertEquals(listOf(violation1, violation2), exception.violations)
    }

    @Test
    fun constructorWithVarargViolationsAndCause() {
        val violation1 = violation("error 1")
        val violation2 = violation("error 2")
        val cause = RuntimeException("cause")

        val exception = ValidationException(violation1, violation2, cause = cause)

        assertEquals("Validation failed:\n\t- error 1\n\t- error 2", exception.message)
        assertEquals(listOf(violation1, violation2), exception.violations)
        assertEquals(cause, exception.cause)
    }

    @Test
    fun constructorWithSingleVarargViolation() {
        val violation = violation("single error")

        val exception = ValidationException(violation)

        assertEquals("Validation failed:\n\t- single error", exception.message)
        assertEquals(listOf(violation), exception.violations)
    }

    @Test
    fun exceptionCanBeThrown() {
        val violation = violation("error")
        val exception = ValidationException(violation)

        val thrown =
            assertFailsWith<ValidationException> {
                throw exception
            }

        assertEquals(exception, thrown)
    }

    @Test
    fun exceptionCanBeCaught() {
        val violation = violation("error")

        try {
            throw ValidationException(violation)
        } catch (e: ValidationException) {
            assertEquals(listOf(violation), e.violations)
        }
    }

    @Test
    fun exceptionIsThrowable() {
        val exception = ValidationException(violation("error"))

        @Suppress("USELESS_IS_CHECK")
        assertTrue(exception is Throwable)
    }
}

@Suppress("UnusedPrivateClass", "Unused")
private class ValidationExceptionCanBeExtended : ValidationException()
