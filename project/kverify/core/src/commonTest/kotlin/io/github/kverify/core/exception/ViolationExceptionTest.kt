package io.github.kverify.core.exception

import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ViolationExceptionTest {
    @Test
    fun storesProvidedViolation() {
        val v = violation("must not be null")

        val exception = ViolationException(v)

        assertEquals(v, exception.violation)
    }

    @Test
    fun violationsListContainsExactlyTheProvidedViolation() {
        val v = violation("must be positive")

        val exception = ViolationException(v)

        assertEquals(listOf(v), exception.violations)
    }

    @Test
    fun violationsListHasSizeOne() {
        val exception = ViolationException(violation("any reason"))

        assertEquals(1, exception.violations.size)
    }

    @Test
    fun messageReflectsSingleViolation() {
        val exception = ViolationException(violation("reason"))

        assertEquals("Validation failed with 1 violation(s)", exception.message)
    }

    @Test
    fun isValidationException() {
        val exception = ViolationException(violation("reason"))

        assertIs<ValidationException>(exception)
    }
}
