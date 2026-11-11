package io.github.kverify.core.exception

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.ViolationReason
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertSame

val ViolationExceptionTest by testSuite {
    testSuite("violation") {
        test("holds the given violation") {
            val violation = ViolationReason("a")

            val exception = ViolationException(violation)

            assertSame(violation, exception.violation)
        }
    }

    testSuite("violations") {
        test("holds only the given violation") {
            val violation = ViolationReason("a")

            val exception = ViolationException(violation)

            assertEquals(listOf<Violation>(violation), exception.violations)
        }
    }

    testSuite("message") {
        test("states one violation") {
            val violation = ViolationReason("a")

            val exception = ViolationException(violation)

            assertEquals("Validation failed with 1 violation(s)", exception.message)
        }
    }

    testSuite("type") {
        test("is a ValidationException") {
            val violation = ViolationReason("a")

            val exception: Throwable = ViolationException(violation)

            assertIs<ValidationException>(exception)
        }
    }
}
