package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.minLengthViolation
import io.github.kverify.rule.set.util.otherPath
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val MinLengthViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = minLengthViolation()
            val right = minLengthViolation()

            assertEquals(left, right)
        }

        test("a different minLength makes violations not equal") {
            val left = minLengthViolation()
            val right = minLengthViolation(minLength = 4)

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = minLengthViolation()
            val right = minLengthViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = minLengthViolation()
            val right = minLengthViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = minLengthViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = minLengthViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = minLengthViolation()
            val right = minLengthViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = minLengthViolation()

            val result = violation.toString()

            val expectedText =
                "MinLengthViolation(minLength=3, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
