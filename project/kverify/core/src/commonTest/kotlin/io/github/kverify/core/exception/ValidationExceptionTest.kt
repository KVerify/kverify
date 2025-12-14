package io.github.kverify.core.exception

import io.github.kverify.core.util.violations
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ValidationExceptionTest {
    private val violationList = violations("error1", "error2")
    private val cause = RuntimeException("root cause")
    private val violation = violation("error")

    @Test
    fun constructorWithMessageAndViolations() {
        val exception = ValidationException("custom message", violationList)

        assertEquals("custom message", exception.message)
        assertEquals(violationList, exception.violations)
    }

    @Test
    fun constructorWithMessageViolationsAndCause() {
        val exception = ValidationException("custom message", violationList, cause)

        assertEquals("custom message", exception.message)
        assertEquals(violationList, exception.violations)
        assertEquals(cause, exception.cause)
    }

    @Test
    fun constructorWithViolationsGeneratesMessage() {
        val exception = ValidationException(violationList)

        assertTrue(exception.message!!.contains("Validation failed"))
        assertTrue(exception.message!!.contains("error1"))
        assertTrue(exception.message!!.contains("error2"))
        assertEquals(violationList, exception.violations)
    }

    @Test
    fun constructorWithViolationsAndCause() {
        val exception = ValidationException(violationList, cause)

        assertTrue(exception.message!!.contains("Validation failed"))
        assertTrue(exception.message!!.contains("error1"))
        assertTrue(exception.message!!.contains("error2"))
        assertEquals(violationList, exception.violations)
        assertEquals(cause, exception.cause)
    }

    @Test
    fun constructorWithSingleViolation() {
        val exception = ValidationException(violation)

        assertTrue(exception.message!!.contains("Validation failed"))
        assertTrue(exception.message!!.contains("error"))
        assertEquals(violation, exception.violations.single())
    }

    @Test
    fun constructorWithSingleViolationAndCause() {
        val exception = ValidationException(violation, cause)

        assertTrue(exception.message!!.contains("Validation failed"))
        assertTrue(exception.message!!.contains("error"))
        assertEquals(violation, exception.violations.single())
        assertEquals(cause, exception.cause)
    }

    @Test
    fun constructorWithVarargViolations() {
        val exception = ValidationException(*violationList.toTypedArray())

        assertTrue(exception.message!!.contains("Validation failed"))
        assertTrue(exception.message!!.contains("error1"))
        assertTrue(exception.message!!.contains("error2"))
        assertEquals(violationList, exception.violations)
    }

    @Test
    fun constructorWithVarargViolationsAndCause() {
        val exception = ValidationException(*violationList.toTypedArray(), cause = cause)

        assertTrue(exception.message!!.contains("Validation failed"))
        assertTrue(exception.message!!.contains("error1"))
        assertTrue(exception.message!!.contains("error2"))
        assertEquals(violationList, exception.violations)
        assertEquals(cause, exception.cause)
    }

    @Test
    fun constructorWithEmptyViolations() {
        val exception = ValidationException(emptyList())

        assertEquals("Validation failed", exception.message)
        assertEquals(emptyList(), exception.violations)
    }

    @Test
    fun constructorWithNullMessage() {
        val exception = ValidationException(null, violationList)

        assertNull(exception.message)
        assertEquals(violationList, exception.violations)
    }

    @Test
    fun messageFormattingWithMultipleViolations() {
        val exception = ValidationException(violationList)

        val message = exception.message!!
        assertTrue(message.contains("Validation failed:"))
        assertTrue(message.contains("- error1"))
        assertTrue(message.contains("- error2"))
    }

    @Test
    fun extendsThrowable() {
        val exception = ValidationException(violations("error"))
        val throwable: Throwable = exception

        assertEquals("error", (throwable as ValidationException).violations.single().reason)
    }

    @Test
    fun canBeCaught() {
        try {
            throw ValidationException(violations("error"))
        } catch (e: ValidationException) {
            assertEquals("error", e.violations.single().reason)
        }
    }

    @Test
    fun causeIsPreserved() {
        val rootCause = IllegalArgumentException("root")
        val middleCause = RuntimeException("middle", rootCause)
        val exception = ValidationException(violations("error"), middleCause)

        assertEquals(middleCause, exception.cause)
        assertEquals(rootCause, exception.cause?.cause)
    }
}
