package io.github.kverify.ruleset

import de.infix.testBalloon.framework.core.testSuite
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val LengthBetweenViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = lengthBetweenViolation()
            val right = lengthBetweenViolation()

            assertEquals(left, right)
        }

        test("a different minLength makes violations not equal") {
            val left = lengthBetweenViolation()
            val right = lengthBetweenViolation(minLength = 2)

            assertNotEquals(left, right)
        }

        test("a different maxLength makes violations not equal") {
            val left = lengthBetweenViolation()
            val right = lengthBetweenViolation(maxLength = 6)

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = lengthBetweenViolation()
            val right = lengthBetweenViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = lengthBetweenViolation()
            val right = lengthBetweenViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = lengthBetweenViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = lengthBetweenViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = lengthBetweenViolation()
            val right = lengthBetweenViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = lengthBetweenViolation()

            val result = violation.toString()

            val expectedText =
                "LengthBetweenViolation(minLength=1, maxLength=5, validationPath=[NamePathSegment(field)], reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
