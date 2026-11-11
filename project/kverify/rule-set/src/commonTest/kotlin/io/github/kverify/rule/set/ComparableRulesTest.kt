package io.github.kverify.rule.set

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.core.context.pathName
import io.github.kverify.rule.set.util.assertNoViolations
import io.github.kverify.rule.set.util.assertViolationPath
import io.github.kverify.rule.set.util.namePath
import io.github.kverify.rule.set.util.violationFor
import io.github.kverify.rule.set.util.violationsFor
import io.github.kverify.rule.set.violation.AtLeastViolation
import io.github.kverify.rule.set.violation.AtMostViolation
import io.github.kverify.rule.set.violation.BetweenViolation
import io.github.kverify.rule.set.violation.GreaterThanViolation
import io.github.kverify.rule.set.violation.LessThanViolation
import kotlin.test.assertEquals
import kotlin.test.assertIs

val ComparableRulesTest by testSuite {
    testSuite("atLeast") {
        test("reports a value below the minimum") {
            val violation = violationFor(4) { atLeast(5) }

            val atLeastViolation = assertIs<AtLeastViolation<*>>(violation)
            assertEquals(5, atLeastViolation.min)
        }

        test("does not report the minimum itself") {
            val violations = violationsFor(5) { atLeast(5) }

            assertNoViolations(violations)
        }

        test("does not report a value above the minimum") {
            val violations = violationsFor(6) { atLeast(5) }

            assertNoViolations(violations)
        }

        test("reports a string below the minimum") {
            val violation = violationFor("a") { atLeast("b") }

            assertIs<AtLeastViolation<*>>(violation)
        }

        test("does not report a string equal to the minimum") {
            val violations = violationsFor("b") { atLeast("b") }

            assertNoViolations(violations)
        }

        test("reports NaN") {
            val violation = violationFor(nan) { atLeast(0.0) }

            assertIs<AtLeastViolation<*>>(violation)
        }

        test("reports a Float NaN") {
            val violation = violationFor(floatNan) { atLeast(0.0f) }

            assertIs<AtLeastViolation<*>>(violation)
        }

        test("reports every value for a NaN minimum") {
            val violation = violationFor(1.0) { atLeast(nan) }

            assertIs<AtLeastViolation<*>>(violation)
        }

        test("does not report -0.0 for a 0.0 minimum") {
            val violations = violationsFor(negativeZero) { atLeast(0.0) }

            assertNoViolations(violations)
        }

        test("does not report a Float -0.0 for a 0.0 minimum") {
            val violations = violationsFor(negativeZeroFloat) { atLeast(0.0f) }

            assertNoViolations(violations)
        }

        test("does not report positive infinity for a finite minimum") {
            val violations = violationsFor(infinity) { atLeast(Double.MAX_VALUE) }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor(4) { atLeast(5) }

            assertEquals("Must be at least 5", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor(4) { atLeast(5, "custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor(4, context) { atLeast(5) }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }

    testSuite("atMost") {
        test("reports a value above the maximum") {
            val violation = violationFor(6) { atMost(5) }

            val atMostViolation = assertIs<AtMostViolation<*>>(violation)
            assertEquals(5, atMostViolation.max)
        }

        test("does not report the maximum itself") {
            val violations = violationsFor(5) { atMost(5) }

            assertNoViolations(violations)
        }

        test("does not report a value below the maximum") {
            val violations = violationsFor(4) { atMost(5) }

            assertNoViolations(violations)
        }

        test("reports a string above the maximum") {
            val violation = violationFor("b") { atMost("a") }

            assertIs<AtMostViolation<*>>(violation)
        }

        test("does not report a string equal to the maximum") {
            val violations = violationsFor("a") { atMost("a") }

            assertNoViolations(violations)
        }

        test("reports NaN") {
            val violation = violationFor(nan) { atMost(10.0) }

            assertIs<AtMostViolation<*>>(violation)
        }

        test("reports a Float NaN") {
            val violation = violationFor(floatNan) { atMost(10.0f) }

            assertIs<AtMostViolation<*>>(violation)
        }

        test("reports every value for a NaN maximum") {
            val violation = violationFor(1.0) { atMost(nan) }

            assertIs<AtMostViolation<*>>(violation)
        }

        test("does not report 0.0 for a -0.0 maximum") {
            val violations = violationsFor(0.0) { atMost(negativeZero) }

            assertNoViolations(violations)
        }

        test("does not report a Float 0.0 for a -0.0 maximum") {
            val violations = violationsFor(0.0f) { atMost(negativeZeroFloat) }

            assertNoViolations(violations)
        }

        test("reports positive infinity for a finite maximum") {
            val violation = violationFor(infinity) { atMost(Double.MAX_VALUE) }

            assertIs<AtMostViolation<*>>(violation)
        }

        test("does not report positive infinity for an infinite maximum") {
            val violations = violationsFor(infinity) { atMost(infinity) }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor(6) { atMost(5) }

            assertEquals("Must be at most 5", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor(6) { atMost(5, "custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor(6, context) { atMost(5) }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }

    testSuite("greaterThan") {
        test("reports a value below the bound") {
            val violation = violationFor(4) { greaterThan(5) }

            val greaterThanViolation = assertIs<GreaterThanViolation<*>>(violation)
            assertEquals(5, greaterThanViolation.minExclusive)
        }

        test("reports the bound itself") {
            val violation = violationFor(5) { greaterThan(5) }

            assertIs<GreaterThanViolation<*>>(violation)
        }

        test("does not report a value above the bound") {
            val violations = violationsFor(6) { greaterThan(5) }

            assertNoViolations(violations)
        }

        test("reports a string equal to the bound") {
            val violation = violationFor("a") { greaterThan("a") }

            assertIs<GreaterThanViolation<*>>(violation)
        }

        test("does not report a string above the bound") {
            val violations = violationsFor("b") { greaterThan("a") }

            assertNoViolations(violations)
        }

        test("reports NaN") {
            val violation = violationFor(nan) { greaterThan(0.0) }

            assertIs<GreaterThanViolation<*>>(violation)
        }

        test("reports a Float NaN") {
            val violation = violationFor(floatNan) { greaterThan(0.0f) }

            assertIs<GreaterThanViolation<*>>(violation)
        }

        test("reports every value for a NaN bound") {
            val violation = violationFor(1.0) { greaterThan(nan) }

            assertIs<GreaterThanViolation<*>>(violation)
        }

        test("reports -0.0 for a 0.0 bound") {
            val violation = violationFor(negativeZero) { greaterThan(0.0) }

            assertIs<GreaterThanViolation<*>>(violation)
        }

        test("reports a Float -0.0 for a 0.0 bound") {
            val violation = violationFor(negativeZeroFloat) { greaterThan(0.0f) }

            assertIs<GreaterThanViolation<*>>(violation)
        }

        test("does not report positive infinity for a finite bound") {
            val violations = violationsFor(infinity) { greaterThan(Double.MAX_VALUE) }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor(5) { greaterThan(5) }

            assertEquals("Must be greater than 5", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor(5) { greaterThan(5, "custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor(5, context) { greaterThan(5) }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }

    testSuite("lessThan") {
        test("reports a value above the bound") {
            val violation = violationFor(6) { lessThan(5) }

            val lessThanViolation = assertIs<LessThanViolation<*>>(violation)
            assertEquals(5, lessThanViolation.maxExclusive)
        }

        test("reports the bound itself") {
            val violation = violationFor(5) { lessThan(5) }

            assertIs<LessThanViolation<*>>(violation)
        }

        test("does not report a value below the bound") {
            val violations = violationsFor(4) { lessThan(5) }

            assertNoViolations(violations)
        }

        test("reports a string equal to the bound") {
            val violation = violationFor("a") { lessThan("a") }

            assertIs<LessThanViolation<*>>(violation)
        }

        test("does not report a string below the bound") {
            val violations = violationsFor("a") { lessThan("b") }

            assertNoViolations(violations)
        }

        test("reports NaN") {
            val violation = violationFor(nan) { lessThan(10.0) }

            assertIs<LessThanViolation<*>>(violation)
        }

        test("reports a Float NaN") {
            val violation = violationFor(floatNan) { lessThan(10.0f) }

            assertIs<LessThanViolation<*>>(violation)
        }

        test("reports every value for a NaN bound") {
            val violation = violationFor(1.0) { lessThan(nan) }

            assertIs<LessThanViolation<*>>(violation)
        }

        test("reports 0.0 for a -0.0 bound") {
            val violation = violationFor(0.0) { lessThan(negativeZero) }

            assertIs<LessThanViolation<*>>(violation)
        }

        test("reports a Float 0.0 for a -0.0 bound") {
            val violation = violationFor(0.0f) { lessThan(negativeZeroFloat) }

            assertIs<LessThanViolation<*>>(violation)
        }

        test("reports positive infinity for an infinite bound") {
            val violation = violationFor(infinity) { lessThan(infinity) }

            assertIs<LessThanViolation<*>>(violation)
        }

        test("does not report a finite value for an infinite bound") {
            val violations = violationsFor(Double.MAX_VALUE) { lessThan(infinity) }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor(5) { lessThan(5) }

            assertEquals("Must be less than 5", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor(5) { lessThan(5, "custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor(5, context) { lessThan(5) }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }

    testSuite("between") {
        test("reports a value below the minimum") {
            val violation = violationFor(1) { between(2, 4) }

            val betweenViolation = assertIs<BetweenViolation<*>>(violation)
            assertEquals(2, betweenViolation.min)
            assertEquals(4, betweenViolation.max)
        }

        test("reports a value above the maximum") {
            val violation = violationFor(5) { between(2, 4) }

            assertIs<BetweenViolation<*>>(violation)
        }

        test("does not report the minimum itself") {
            val violations = violationsFor(2) { between(2, 4) }

            assertNoViolations(violations)
        }

        test("does not report the maximum itself") {
            val violations = violationsFor(4) { between(2, 4) }

            assertNoViolations(violations)
        }

        test("does not report a value inside the range") {
            val violations = violationsFor(3) { between(2, 4) }

            assertNoViolations(violations)
        }

        test("reports a string outside the range") {
            val violation = violationFor("a") { between("b", "d") }

            assertIs<BetweenViolation<*>>(violation)
        }

        test("does not report a string inside the range") {
            val violations = violationsFor("c") { between("b", "d") }

            assertNoViolations(violations)
        }

        test("reports NaN") {
            val violation = violationFor(nan) { between(0.0, 10.0) }

            assertIs<BetweenViolation<*>>(violation)
        }

        test("reports a Float NaN") {
            val violation = violationFor(floatNan) { between(0.0f, 10.0f) }

            assertIs<BetweenViolation<*>>(violation)
        }

        test("reports every value for a NaN minimum") {
            val violation = violationFor(1.0) { between(nan, 2.0) }

            assertIs<BetweenViolation<*>>(violation)
        }

        test("reports every value for a NaN maximum") {
            val violation = violationFor(1.0) { between(0.0, nan) }

            assertIs<BetweenViolation<*>>(violation)
        }

        test("does not report -0.0 for a 0.0 minimum") {
            val violations = violationsFor(negativeZero) { between(0.0, 1.0) }

            assertNoViolations(violations)
        }

        test("does not report a Float -0.0 for a 0.0 minimum") {
            val violations = violationsFor(negativeZeroFloat) { between(0.0f, 1.0f) }

            assertNoViolations(violations)
        }

        test("does not report 0.0 for a -0.0 maximum") {
            val violations = violationsFor(0.0) { between(-1.0, negativeZero) }

            assertNoViolations(violations)
        }

        test("reports positive infinity for a finite maximum") {
            val violation = violationFor(infinity) { between(0.0, Double.MAX_VALUE) }

            assertIs<BetweenViolation<*>>(violation)
        }

        test("does not report positive infinity for an infinite maximum") {
            val violations = violationsFor(infinity) { between(0.0, infinity) }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor(1) { between(2, 4) }

            assertEquals("Must be between 2 and 4", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor(1) { between(2, 4, "custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor(1, context) { between(2, 4) }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }
}

private val nan = Double.NaN

private val floatNan = Float.NaN

private val negativeZero = -0.0

private val negativeZeroFloat = -0.0f

private val infinity = Double.POSITIVE_INFINITY
