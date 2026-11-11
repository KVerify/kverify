package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.notNullViolation
import io.github.kverify.rule.set.util.otherPath
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val NotNullViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = notNullViolation()
            val right = notNullViolation()

            assertEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = notNullViolation()
            val right = notNullViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = notNullViolation()
            val right = notNullViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = notNullViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = notNullViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = notNullViolation()
            val right = notNullViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = notNullViolation()

            val result = violation.toString()

            val expectedText =
                "NotNullViolation(validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
