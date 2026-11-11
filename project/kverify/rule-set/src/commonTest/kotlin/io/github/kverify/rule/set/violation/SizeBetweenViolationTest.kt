package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.otherPath
import io.github.kverify.rule.set.util.sizeBetweenViolation
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val SizeBetweenViolationTest by testSuite {
    testSuite("equals") {
        test("violations with equal fields are equal") {
            val left = sizeBetweenViolation()
            val right = sizeBetweenViolation()

            assertEquals(left, right)
        }

        test("a different minSize makes violations not equal") {
            val left = sizeBetweenViolation()
            val right = sizeBetweenViolation(minSize = 2)

            assertNotEquals(left, right)
        }

        test("a different maxSize makes violations not equal") {
            val left = sizeBetweenViolation()
            val right = sizeBetweenViolation(maxSize = 6)

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = sizeBetweenViolation()
            val right = sizeBetweenViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = sizeBetweenViolation()
            val right = sizeBetweenViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = sizeBetweenViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = sizeBetweenViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal fields have equal hash codes") {
            val left = sizeBetweenViolation()
            val right = sizeBetweenViolation()

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = sizeBetweenViolation()

            val result = violation.toString()

            val expectedText =
                "SizeBetweenViolation(minSize=1, maxSize=5, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
