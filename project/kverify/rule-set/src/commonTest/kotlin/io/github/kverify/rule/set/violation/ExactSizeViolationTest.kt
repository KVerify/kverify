package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.exactSizeViolation
import io.github.kverify.rule.set.util.otherPath
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val ExactSizeViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = exactSizeViolation()
            val right = exactSizeViolation()

            assertEquals(left, right)
        }

        test("a different expectedSize makes violations not equal") {
            val left = exactSizeViolation()
            val right = exactSizeViolation(expectedSize = 4)

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = exactSizeViolation()
            val right = exactSizeViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = exactSizeViolation()
            val right = exactSizeViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = exactSizeViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = exactSizeViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = exactSizeViolation()
            val right = exactSizeViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = exactSizeViolation()

            val result = violation.toString()

            val expectedText =
                "ExactSizeViolation(expectedSize=3, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
