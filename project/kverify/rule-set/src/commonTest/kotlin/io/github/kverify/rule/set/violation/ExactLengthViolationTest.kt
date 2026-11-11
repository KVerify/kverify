package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.exactLengthViolation
import io.github.kverify.rule.set.util.otherPath
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val ExactLengthViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = exactLengthViolation()
            val right = exactLengthViolation()

            assertEquals(left, right)
        }

        test("a different expectedLength makes violations not equal") {
            val left = exactLengthViolation()
            val right = exactLengthViolation(expectedLength = 4)

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = exactLengthViolation()
            val right = exactLengthViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = exactLengthViolation()
            val right = exactLengthViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = exactLengthViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = exactLengthViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = exactLengthViolation()
            val right = exactLengthViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = exactLengthViolation()

            val result = violation.toString()

            val expectedText =
                "ExactLengthViolation(expectedLength=3, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
