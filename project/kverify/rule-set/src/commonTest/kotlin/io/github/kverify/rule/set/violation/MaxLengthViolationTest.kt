package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.maxLengthViolation
import io.github.kverify.rule.set.util.otherPath
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val MaxLengthViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = maxLengthViolation()
            val right = maxLengthViolation()

            assertEquals(left, right)
        }

        test("a different maxLength makes violations not equal") {
            val left = maxLengthViolation()
            val right = maxLengthViolation(maxLength = 4)

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = maxLengthViolation()
            val right = maxLengthViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = maxLengthViolation()
            val right = maxLengthViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = maxLengthViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = maxLengthViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = maxLengthViolation()
            val right = maxLengthViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = maxLengthViolation()

            val result = violation.toString()

            val expectedText =
                "MaxLengthViolation(maxLength=3, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
