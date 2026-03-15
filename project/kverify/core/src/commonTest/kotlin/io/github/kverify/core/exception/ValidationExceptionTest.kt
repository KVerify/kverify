package io.github.kverify.core.exception

import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ValidationExceptionTest {
    @Test
    fun storesProvidedViolations() {
        val violations = listOf(violation("first"), violation("second"))

        val exception = ValidationException(violations)

        assertEquals(violations, exception.violations)
    }

    @Test
    fun messageReflectsViolationCount() {
        val violations = listOf(violation("a"), violation("b"), violation("c"))

        val exception = ValidationException(violations)

        assertEquals("Validation failed with ${violations.size} violation(s)", exception.message)
    }

    @Test
    fun isRuntimeException() {
        val exception = ValidationException(emptyList())

        assertIs<RuntimeException>(exception)
    }
}
