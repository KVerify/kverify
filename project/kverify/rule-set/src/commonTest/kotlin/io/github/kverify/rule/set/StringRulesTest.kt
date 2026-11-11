package io.github.kverify.rule.set

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.core.context.pathName
import io.github.kverify.rule.set.util.assertNoViolations
import io.github.kverify.rule.set.util.assertViolationPath
import io.github.kverify.rule.set.util.namePath
import io.github.kverify.rule.set.util.violationFor
import io.github.kverify.rule.set.util.violationsFor
import io.github.kverify.rule.set.violation.ExactLengthViolation
import io.github.kverify.rule.set.violation.LengthRangeViolation
import io.github.kverify.rule.set.violation.MaxLengthViolation
import io.github.kverify.rule.set.violation.MinLengthViolation
import io.github.kverify.rule.set.violation.NotBlankViolation
import io.github.kverify.rule.set.violation.PatternViolation
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertSame

val StringRulesTest by testSuite {
    testSuite("notBlank") {
        test("reports an empty value") {
            val violation = violationFor("") { notBlank() }

            assertIs<NotBlankViolation>(violation)
        }

        test("reports a value made of whitespace only") {
            val violation = violationFor(" \t\n") { notBlank() }

            assertIs<NotBlankViolation>(violation)
        }

        test("does not report a value with a visible character") {
            val violations = violationsFor(" a ") { notBlank() }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor("") { notBlank() }

            assertEquals("Must not be blank", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor("") { notBlank("custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor("", context) { notBlank() }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }

    testSuite("minLength") {
        test("reports a value shorter than the minimum") {
            val violation = violationFor("ab") { minLength(3) }

            val minLengthViolation = assertIs<MinLengthViolation>(violation)
            assertEquals(3, minLengthViolation.minLength)
        }

        test("does not report a value of exactly the minimum length") {
            val violations = violationsFor("abc") { minLength(3) }

            assertNoViolations(violations)
        }

        test("does not report a value longer than the minimum") {
            val violations = violationsFor("abcd") { minLength(3) }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor("ab") { minLength(3) }

            assertEquals("Length must be at least 3", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor("ab") { minLength(3, "custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor("ab", context) { minLength(3) }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }

    testSuite("maxLength") {
        test("reports a value longer than the maximum") {
            val violation = violationFor("abcd") { maxLength(3) }

            val maxLengthViolation = assertIs<MaxLengthViolation>(violation)
            assertEquals(3, maxLengthViolation.maxLength)
        }

        test("does not report a value of exactly the maximum length") {
            val violations = violationsFor("abc") { maxLength(3) }

            assertNoViolations(violations)
        }

        test("does not report a value shorter than the maximum") {
            val violations = violationsFor("ab") { maxLength(3) }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor("abcd") { maxLength(3) }

            assertEquals("Length must be at most 3", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor("abcd") { maxLength(3, "custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor("abcd", context) { maxLength(3) }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }

    testSuite("lengthRange") {
        test("reports a value shorter than the minimum") {
            val violation = violationFor("a") { lengthRange(2, 4) }

            val lengthRangeViolation = assertIs<LengthRangeViolation>(violation)
            assertEquals(2, lengthRangeViolation.minLength)
            assertEquals(4, lengthRangeViolation.maxLength)
        }

        test("reports a value longer than the maximum") {
            val violation = violationFor("abcde") { lengthRange(2, 4) }

            assertIs<LengthRangeViolation>(violation)
        }

        test("does not report a value of exactly the minimum length") {
            val violations = violationsFor("ab") { lengthRange(2, 4) }

            assertNoViolations(violations)
        }

        test("does not report a value of exactly the maximum length") {
            val violations = violationsFor("abcd") { lengthRange(2, 4) }

            assertNoViolations(violations)
        }

        test("does not report a value inside the range") {
            val violations = violationsFor("abc") { lengthRange(2, 4) }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor("a") { lengthRange(2, 4) }

            assertEquals("Length must be between 2 and 4", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor("a") { lengthRange(2, 4, "custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor("a", context) { lengthRange(2, 4) }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }

    testSuite("exactLength") {
        test("reports a value shorter than the expected length") {
            val violation = violationFor("ab") { exactLength(3) }

            val exactLengthViolation = assertIs<ExactLengthViolation>(violation)
            assertEquals(3, exactLengthViolation.expectedLength)
        }

        test("reports a value longer than the expected length") {
            val violation = violationFor("abcd") { exactLength(3) }

            assertIs<ExactLengthViolation>(violation)
        }

        test("does not report a value of exactly the expected length") {
            val violations = violationsFor("abc") { exactLength(3) }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor("ab") { exactLength(3) }

            assertEquals("Length must be exactly 3", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor("ab") { exactLength(3, "custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor("ab", context) { exactLength(3) }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }

    testSuite("matches") {
        test("reports a value that does not match") {
            val regex = Regex("[a-z]+")

            val violation = violationFor("123") { matches(regex) }

            val patternViolation = assertIs<PatternViolation>(violation)
            assertSame(regex, patternViolation.regex)
        }

        test("reports a value that matches only partly") {
            val regex = Regex("[a-z]+")

            val violation = violationFor("ab1") { matches(regex) }

            assertIs<PatternViolation>(violation)
        }

        test("reports an empty value when the pattern needs at least one character") {
            val regex = Regex("[a-z]+")

            val violation = violationFor("") { matches(regex) }

            assertIs<PatternViolation>(violation)
        }

        test("does not report a value that matches entirely") {
            val regex = Regex("[a-z]+")

            val violations = violationsFor("abc") { matches(regex) }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val regex = Regex("[a-z]+")

            val violation = violationFor("1") { matches(regex) }

            assertEquals("Must match pattern [a-z]+", violation.reason)
        }

        test("uses the given reason") {
            val regex = Regex("[a-z]+")

            val violation = violationFor("1") { matches(regex, "custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val regex = Regex("[a-z]+")
            val context = pathName("field")

            val violation = violationFor("1", context) { matches(regex) }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }
}
