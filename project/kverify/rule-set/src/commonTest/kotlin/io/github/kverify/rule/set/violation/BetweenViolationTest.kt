package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.betweenViolation
import io.github.kverify.rule.set.util.otherPath
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val BetweenViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = betweenViolation()
            val right = betweenViolation()

            assertEquals(left, right)
        }

        test("a different min makes violations not equal") {
            val left = betweenViolation()
            val right = betweenViolation(min = 2)

            assertNotEquals(left, right)
        }

        test("a different max makes violations not equal") {
            val left = betweenViolation()
            val right = betweenViolation(max = 6)

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = betweenViolation()
            val right = betweenViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = betweenViolation()
            val right = betweenViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = betweenViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = betweenViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = betweenViolation()
            val right = betweenViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = betweenViolation()

            val result = violation.toString()

            val expectedText =
                "BetweenViolation(min=1, max=5, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
