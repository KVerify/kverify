package io.github.kverify.core.exception

import io.github.kverify.core.violation.ViolationReason
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ThrowingValidationContextExceptionTest {
    @Test
    fun constructorWithAllParameters() {
        val message = "Custom message"
        val violation = violation("error")
        val cause = RuntimeException("cause")

        val exception =
            ThrowingValidationContextException(
                message = message,
                violation = violation,
                cause = cause,
            )

        assertEquals(message, exception.message)
        assertEquals(violation, exception.violation)
        assertEquals(listOf(violation), exception.violations)
        assertEquals(cause, exception.cause)
    }

    @Test
    fun constructorWithMessageAndViolation() {
        val message = "Custom message"
        val violation = violation("error")

        val exception =
            ThrowingValidationContextException(
                message = message,
                violation = violation,
            )

        assertEquals(message, exception.message)
        assertEquals(violation, exception.violation)
        assertEquals(listOf(violation), exception.violations)
        assertNull(exception.cause)
    }

    @Test
    fun constructorWithViolationOnly() {
        val violation = violation("validation error")

        val exception = ThrowingValidationContextException(violation)

        assertEquals("Validation failed:\n\t- validation error", exception.message)
        assertEquals(violation, exception.violation)
        assertEquals(listOf(violation), exception.violations)
        assertNull(exception.cause)
    }

    @Test
    fun constructorWithViolationAndCause() {
        val violation = violation("error")
        val cause = IllegalArgumentException("cause")

        val exception =
            ThrowingValidationContextException(
                violation = violation,
                cause = cause,
            )

        assertEquals("Validation failed:\n\t- error", exception.message)
        assertEquals(violation, exception.violation)
        assertEquals(cause, exception.cause)
    }

    @Test
    fun defaultConstructorGeneratesMessage() {
        val violation = ViolationReason("field is invalid")

        val exception = ThrowingValidationContextException(violation)

        assertEquals("Validation failed:\n\t- field is invalid", exception.message)
    }

    @Test
    fun violationPropertyMatchesViolationsListFirstElement() {
        val violation = violation("error")

        val exception = ThrowingValidationContextException(violation)

        assertEquals(exception.violation, exception.violations.first())
        assertEquals(1, exception.violations.size)
    }

    @Test
    fun exceptionCanBeThrown() {
        val violation = violation("error")
        val exception = ThrowingValidationContextException(violation)

        val thrown =
            kotlin
                .runCatching {
                    throw exception
                }.exceptionOrNull()

        assertEquals(exception, thrown)
    }

    @Test
    fun exceptionCanBeCaught() {
        val violation = violation("error")

        try {
            throw ThrowingValidationContextException(violation)
        } catch (e: ThrowingValidationContextException) {
            assertEquals(violation, e.violation)
        }
    }

    @Test
    fun exceptionIsValidationException() {
        val exception = ThrowingValidationContextException(violation("error"))

        assertTrue(exception is ValidationException)
    }

    @Test
    fun exceptionIsThrowable() {
        val exception = ThrowingValidationContextException(violation("error"))

        assertTrue(exception is Throwable)
    }

    @Test
    fun exceptionWithComplexViolationReason() {
        val violation = ViolationReason("Complex reason with\nmultiple lines\nand special chars !@#$%")

        val exception = ThrowingValidationContextException(violation)

        val expectedMessage = "Validation failed:\n\t- Complex reason with\nmultiple lines\nand special chars !@#$%"
        assertEquals(expectedMessage, exception.message)
        assertEquals(violation, exception.violation)
    }

    @Test
    fun exceptionPreservesNullMessage() {
        val violation = violation("error")

        val exception =
            ThrowingValidationContextException(
                message = null,
                violation = violation,
            )

        assertNull(exception.message)
        assertEquals(violation, exception.violation)
    }

    @Test
    fun exceptionCanBeExtended() {
        val violation = violation("error")
        val customException = object : ThrowingValidationContextException(violation) {}

        assertEquals("Validation failed:\n\t- error", customException.message)
        assertEquals(violation, customException.violation)
    }

    @Test
    fun exceptionWithEmptyStringViolation() {
        val violation = ViolationReason("")

        val exception = ThrowingValidationContextException(violation)

        assertEquals("Validation failed:\n\t- ", exception.message)
        assertEquals(violation, exception.violation)
    }

    @Test
    fun multipleInstancesWithSameViolationAreIndependent() {
        val violation = violation("error")
        val exception1 = ThrowingValidationContextException(violation)
        val exception2 = ThrowingValidationContextException(violation)

        assertEquals(exception1.violation, exception2.violation)
        assertEquals(exception1.message, exception2.message)
    }

    @Test
    fun exceptionMessageFormatWithCustomMessage() {
        val customMessage = "Operation failed due to invalid input"
        val violation = violation("value must be positive")

        val exception =
            ThrowingValidationContextException(
                message = customMessage,
                violation = violation,
            )

        assertEquals(customMessage, exception.message)
        assertEquals(violation, exception.violation)
    }
}

@Suppress("UnusedPrivateClass", "Unused")
private class ThrowingValidationContextExceptionCanBeExtended : ThrowingValidationContextException(violation("error"))
