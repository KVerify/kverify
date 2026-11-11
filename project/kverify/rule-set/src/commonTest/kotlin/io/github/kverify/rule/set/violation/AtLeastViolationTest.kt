package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.atLeastViolation
import io.github.kverify.rule.set.util.otherPath
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val AtLeastViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = atLeastViolation()
            val right = atLeastViolation()

            assertEquals(left, right)
        }

        test("a different min makes violations not equal") {
            val left = atLeastViolation()
            val right = atLeastViolation(min = 2)

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = atLeastViolation()
            val right = atLeastViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = atLeastViolation()
            val right = atLeastViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = atLeastViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = atLeastViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = atLeastViolation()
            val right = atLeastViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = atLeastViolation()

            val result = violation.toString()

            val expectedText =
                "AtLeastViolation(min=1, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
