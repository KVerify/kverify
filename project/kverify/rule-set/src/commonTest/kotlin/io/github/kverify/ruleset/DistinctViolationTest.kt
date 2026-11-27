package io.github.kverify.ruleset

import de.infix.testBalloon.framework.core.testSuite
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val DistinctViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = distinctViolation()
            val right = distinctViolation()

            assertEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = distinctViolation()
            val right = distinctViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = distinctViolation()
            val right = distinctViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = distinctViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = distinctViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = distinctViolation()
            val right = distinctViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = distinctViolation()

            val result = violation.toString()

            val expectedText =
                "DistinctViolation(validationPath=[NamePathSegment(field)], reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
