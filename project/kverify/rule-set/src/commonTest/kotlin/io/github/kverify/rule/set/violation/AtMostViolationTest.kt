package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.atMostViolation
import io.github.kverify.rule.set.util.otherPath
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val AtMostViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = atMostViolation()
            val right = atMostViolation()

            assertEquals(left, right)
        }

        test("a different max makes violations not equal") {
            val left = atMostViolation()
            val right = atMostViolation(max = 2)

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = atMostViolation()
            val right = atMostViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = atMostViolation()
            val right = atMostViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = atMostViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = atMostViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = atMostViolation()
            val right = atMostViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = atMostViolation()

            val result = violation.toString()

            val expectedText =
                "AtMostViolation(max=1, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
