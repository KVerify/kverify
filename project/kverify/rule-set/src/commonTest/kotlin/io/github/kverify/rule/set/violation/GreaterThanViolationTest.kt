package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.greaterThanViolation
import io.github.kverify.rule.set.util.otherPath
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val GreaterThanViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = greaterThanViolation()
            val right = greaterThanViolation()

            assertEquals(left, right)
        }

        test("a different minExclusive makes violations not equal") {
            val left = greaterThanViolation()
            val right = greaterThanViolation(minExclusive = 2)

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = greaterThanViolation()
            val right = greaterThanViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = greaterThanViolation()
            val right = greaterThanViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = greaterThanViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = greaterThanViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = greaterThanViolation()
            val right = greaterThanViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = greaterThanViolation()

            val result = violation.toString()

            val expectedText =
                "GreaterThanViolation(minExclusive=1, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
