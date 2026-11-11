package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.maxSizeViolation
import io.github.kverify.rule.set.util.otherPath
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val MaxSizeViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = maxSizeViolation()
            val right = maxSizeViolation()

            assertEquals(left, right)
        }

        test("a different maxSize makes violations not equal") {
            val left = maxSizeViolation()
            val right = maxSizeViolation(maxSize = 4)

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = maxSizeViolation()
            val right = maxSizeViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = maxSizeViolation()
            val right = maxSizeViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = maxSizeViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = maxSizeViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = maxSizeViolation()
            val right = maxSizeViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = maxSizeViolation()

            val result = violation.toString()

            val expectedText =
                "MaxSizeViolation(maxSize=3, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
