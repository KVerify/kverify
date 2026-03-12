package io.github.kverify.core.exception

import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ValidationExceptionTest {
    @Test
    fun violations_accessible() {
        val first = violation("first")
        val second = violation("second")
        val violations = listOf(first, second)

        val ex = ValidationException(violations)

        assertEquals(violations, ex.violations)
    }

    @Test
    fun message_reflectsViolationCount() {
        val violations = listOf(violation("a"), violation("b"), violation("c"))

        val ex = ValidationException(violations)

        val expectedCount = violations.size
        assertEquals("Validation failed with $expectedCount violation(s)", ex.message)
    }

    @Test
    fun isRuntimeException() {
        assertIs<RuntimeException>(ValidationException(emptyList()))
    }
}
