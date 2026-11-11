package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.notBlankViolation
import io.github.kverify.rule.set.util.otherPath
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val NotBlankViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = notBlankViolation()
            val right = notBlankViolation()

            assertEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = notBlankViolation()
            val right = notBlankViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = notBlankViolation()
            val right = notBlankViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = notBlankViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = notBlankViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = notBlankViolation()
            val right = notBlankViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = notBlankViolation()

            val result = violation.toString()

            val expectedText =
                "NotBlankViolation(validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
