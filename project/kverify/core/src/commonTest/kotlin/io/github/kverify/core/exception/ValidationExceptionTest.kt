package io.github.kverify.core.exception

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.ViolationReason
import kotlin.test.assertEquals
import kotlin.test.assertTrue

val ValidationExceptionTest by testSuite {
    testSuite("violations") {
        test("holds the given violations") {
            val violations = listOf<Violation>(ViolationReason("a"), ViolationReason("b"))

            val exception = ValidationException(violations)

            assertEquals(violations, exception.violations)
        }

        test("holds no violations when the given list is empty") {
            val exception = ValidationException(listOf())

            assertTrue(exception.violations.isEmpty())
        }
    }

    testSuite("message") {
        test("states the number of violations") {
            val violations = listOf<Violation>(ViolationReason("a"), ViolationReason("b"))

            val exception = ValidationException(violations)

            assertEquals("Validation failed with 2 violation(s)", exception.message)
        }
    }
}
