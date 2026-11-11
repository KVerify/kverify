package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.minSizeViolation
import io.github.kverify.rule.set.util.otherPath
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val MinSizeViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = minSizeViolation()
            val right = minSizeViolation()

            assertEquals(left, right)
        }

        test("a different minSize makes violations not equal") {
            val left = minSizeViolation()
            val right = minSizeViolation(minSize = 4)

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = minSizeViolation()
            val right = minSizeViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = minSizeViolation()
            val right = minSizeViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = minSizeViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = minSizeViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = minSizeViolation()
            val right = minSizeViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = minSizeViolation()

            val result = violation.toString()

            val expectedText =
                "MinSizeViolation(minSize=3, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
