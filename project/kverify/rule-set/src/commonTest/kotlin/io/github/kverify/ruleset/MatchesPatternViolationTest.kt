package io.github.kverify.ruleset

import de.infix.testBalloon.framework.core.testSuite
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val MatchesPatternViolationTest by testSuite {
    testSuite("equals") {
        test("violations with the same pattern and options are equal") {
            val left = matchesPatternViolation(regex = Regex("a", RegexOption.IGNORE_CASE))
            val right = matchesPatternViolation(regex = Regex("a", RegexOption.IGNORE_CASE))

            assertEquals(left, right)
        }

        test("a different pattern makes violations not equal") {
            val left = matchesPatternViolation()
            val right = matchesPatternViolation(regex = Regex("b"))

            assertNotEquals(left, right)
        }

        test("different options make violations not equal") {
            val left = matchesPatternViolation()
            val right = matchesPatternViolation(regex = Regex("a", RegexOption.IGNORE_CASE))

            assertNotEquals(left, right)
        }

        test("a different path makes violations not equal") {
            val left = matchesPatternViolation()
            val right = matchesPatternViolation(path = otherPath)

            assertNotEquals(left, right)
        }

        test("a different reason makes violations not equal") {
            val left = matchesPatternViolation()
            val right = matchesPatternViolation(reason = "other")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = matchesPatternViolation()

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = matchesPatternViolation()

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with the same pattern and options have equal hash codes") {
            val left = matchesPatternViolation(regex = Regex("a", RegexOption.IGNORE_CASE))
            val right = matchesPatternViolation(regex = Regex("a", RegexOption.IGNORE_CASE))

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows all fields") {
            val violation = matchesPatternViolation()

            val result = violation.toString()

            val expectedText =
                "MatchesPatternViolation(regex=a, validationPath=ValidationPath(\"field\"), reason=reason)"
            assertEquals(expectedText, result)
        }
    }
}
