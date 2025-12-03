package io.github.kverify.core.exception

import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ThrowingValidationContextExceptionTest {
    private val violation = violation("error")
    private val cause = RuntimeException("root cause")

    @Test
    fun constructorWithViolationGeneratesMessage() {
        val exception = ThrowingValidationContextException(violation)

        assertTrue(exception.message!!.contains("Validation failed:"))
        assertTrue(exception.message!!.contains("- error"))
        assertEquals(violation, exception.violation)
    }

    @Test
    fun constructorWithViolationAndCause() {
        val exception = ThrowingValidationContextException(violation, cause)

        assertTrue(exception.message!!.contains("Validation failed:"))
        assertTrue(exception.message!!.contains("- error"))
        assertEquals(violation, exception.violation)
        assertEquals(cause, exception.cause)
    }

    @Test
    fun constructorWithCustomMessage() {
        val exception = ThrowingValidationContextException("custom message", violation)

        assertEquals("custom message", exception.message)
        assertEquals(violation, exception.violation)
    }

    @Test
    fun constructorWithCustomMessageAndCause() {
        val exception = ThrowingValidationContextException("custom message", violation, cause)

        assertEquals("custom message", exception.message)
        assertEquals(violation, exception.violation)
        assertEquals(cause, exception.cause)
    }

    @Test
    fun constructorWithNullMessage() {
        val exception = ThrowingValidationContextException(null, violation)

        assertNull(exception.message)
        assertEquals(violation, exception.violation)
    }

    @Test
    fun violationsListContainsSingleViolation() {
        val exception = ThrowingValidationContextException(violation)

        assertEquals(violation, exception.violations.single())
    }

    @Test
    fun extendsValidationException() {
        val exception: ValidationException = ThrowingValidationContextException(violation)

        assertEquals(violation, exception.violations.single())
    }

    @Test
    fun canBeCaught() {
        try {
            throw ThrowingValidationContextException(violation)
        } catch (e: ThrowingValidationContextException) {
            assertEquals(violation, e.violation)
        }
    }

    @Test
    fun canBeCaughtAsValidationException() {
        try {
            throw ThrowingValidationContextException(violation)
        } catch (e: ValidationException) {
            assertEquals(violation, e.violations.single())
        }
    }

    @Test
    fun messageFormattingWithViolation() {
        val exception = ThrowingValidationContextException(violation)

        val message = exception.message!!
        assertTrue(message.contains("Validation failed:"))
        assertTrue(message.contains("- test error message"))
    }

    @Test
    fun causeIsPreserved() {
        val rootCause = IllegalArgumentException("root")
        val middleCause = RuntimeException("middle", rootCause)
        val exception = ThrowingValidationContextException(violation, middleCause)

        assertEquals(middleCause, exception.cause)
        assertEquals(rootCause, exception.cause?.cause)
    }

    @Test
    fun violationIsAccessibleFromViolations() {
        val exception = ThrowingValidationContextException(violation)

        assertEquals(exception.violation, exception.violations.single())
    }
}
