package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.lengthRangeViolation
import io.github.kverify.rule.set.util.otherPath
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val LengthRangeViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = lengthRangeViolation()
            val right = lengthRangeViolation()

            assertEquals(left, right)
        }

        test("a different minLength makes violations not equal") {
            val left = lengthRangeViolation()
            val right = lengthRangeViolation(minLength = 2)

            assertNotEquals(left, right)
        }

        test("a different maxLength makes violations not equal") {
            val left = lengthRangeViolation()
            val right = lengthRangeViolation(maxLength = 6)

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = lengthRangeViolation()
            val right = lengthRangeViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = lengthRangeViolation()
            val right = lengthRangeViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = lengthRangeViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = lengthRangeViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = lengthRangeViolation()
            val right = lengthRangeViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = lengthRangeViolation()

            val result = violation.toString()

            val expectedText =
                "LengthRangeViolation(minLength=1, maxLength=5, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
