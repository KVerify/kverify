package io.github.kverify.rule.set.violation

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.rule.set.util.otherPath
import io.github.kverify.rule.set.util.patternViolation
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val PatternViolationTest by testSuite {
    testSuite("equals") {
        test("violations with the same pattern and options are equal") {
            val left = patternViolation(regex = Regex("a", RegexOption.IGNORE_CASE))
            val right = patternViolation(regex = Regex("a", RegexOption.IGNORE_CASE))

            assertEquals(left, right)
        }

        test("a different pattern makes violations not equal") {
            val left = patternViolation()
            val right = patternViolation(regex = Regex("b"))

            assertNotEquals(left, right)
        }

        test("different options make violations not equal") {
            val left = patternViolation()
            val right = patternViolation(regex = Regex("a", RegexOption.IGNORE_CASE))

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = patternViolation()
            val right = patternViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = patternViolation()
            val right = patternViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = patternViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = patternViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with the same pattern and options have equal hash codes") {
            val left = patternViolation(regex = Regex("a", RegexOption.IGNORE_CASE))
            val right = patternViolation(regex = Regex("a", RegexOption.IGNORE_CASE))

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = patternViolation()

            val result = violation.toString()

            val expectedText =
                "PatternViolation(regex=a, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
