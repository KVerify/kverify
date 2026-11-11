package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.lessThanViolation
import io.github.kverify.rule.set.util.otherPath
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val LessThanViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = lessThanViolation()
            val right = lessThanViolation()

            assertEquals(left, right)
        }

        test("a different maxExclusive makes violations not equal") {
            val left = lessThanViolation()
            val right = lessThanViolation(maxExclusive = 2)

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = lessThanViolation()
            val right = lessThanViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = lessThanViolation()
            val right = lessThanViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = lessThanViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = lessThanViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = lessThanViolation()
            val right = lessThanViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = lessThanViolation()

            val result = violation.toString()

            val expectedText =
                "LessThanViolation(maxExclusive=1, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
