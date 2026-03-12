package io.github.kverify.core.exception

import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ViolationExceptionTest {
    @Test
    fun violation_accessible() {
        val cause = violation("access denied")

        val ex = ViolationException(cause)

        assertEquals(cause, ex.violation)
    }

    @Test
    fun violations_containsSingleViolation() {
        val cause = violation("access denied")

        val ex = ViolationException(cause)

        assertEquals(cause, ex.violations.single())
    }

    @Test
    fun isValidationException() {
        assertIs<ValidationException>(ViolationException(violation("error")))
    }

    @Test
    fun message_reflectsSingleViolation() {
        val ex = ViolationException(violation("error"))
        val expectedCount = 1

        assertEquals("Validation failed with $expectedCount violation(s)", ex.message)
    }
}
