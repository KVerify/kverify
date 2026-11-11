package io.github.kverify.rule.set

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.core.context.pathName
import io.github.kverify.rule.set.util.assertNoViolations
import io.github.kverify.rule.set.util.assertViolationPath
import io.github.kverify.rule.set.util.namePath
import io.github.kverify.rule.set.util.violationFor
import io.github.kverify.rule.set.util.violationsFor
import io.github.kverify.rule.set.violation.DistinctViolation
import io.github.kverify.rule.set.violation.ExactSizeViolation
import io.github.kverify.rule.set.violation.MaxSizeViolation
import io.github.kverify.rule.set.violation.MinSizeViolation
import io.github.kverify.rule.set.violation.SizeBetweenViolation
import kotlin.test.assertEquals
import kotlin.test.assertIs

val CollectionRulesTest by testSuite {
    testSuite("minSize") {
        test("reports a collection smaller than the minimum") {
            val violation = violationFor(listOf(1)) { minSize(2) }

            val minSizeViolation = assertIs<MinSizeViolation>(violation)
            assertEquals(2, minSizeViolation.minSize)
        }

        test("does not report a collection of exactly the minimum size") {
            val violations = violationsFor(listOf(1, 2)) { minSize(2) }

            assertNoViolations(violations)
        }

        test("does not report a collection larger than the minimum") {
            val violations = violationsFor(listOf(1, 2, 3)) { minSize(2) }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor(listOf(1)) { minSize(2) }

            assertEquals("Size must be at least 2", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor(listOf(1)) { minSize(2, "custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor(listOf(1), context) { minSize(2) }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }

    testSuite("maxSize") {
        test("reports a collection larger than the maximum") {
            val violation = violationFor(listOf(1, 2, 3)) { maxSize(2) }

            val maxSizeViolation = assertIs<MaxSizeViolation>(violation)
            assertEquals(2, maxSizeViolation.maxSize)
        }

        test("does not report a collection of exactly the maximum size") {
            val violations = violationsFor(listOf(1, 2)) { maxSize(2) }

            assertNoViolations(violations)
        }

        test("does not report a collection smaller than the maximum") {
            val violations = violationsFor(listOf(1)) { maxSize(2) }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor(listOf(1, 2, 3)) { maxSize(2) }

            assertEquals("Size must be at most 2", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor(listOf(1, 2, 3)) { maxSize(2, "custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor(listOf(1, 2, 3), context) { maxSize(2) }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }

    testSuite("sizeBetween") {
        test("reports a collection smaller than the minimum") {
            val violation = violationFor(emptyList<Int>()) { sizeBetween(1, 2) }

            val sizeBetweenViolation = assertIs<SizeBetweenViolation>(violation)
            assertEquals(1, sizeBetweenViolation.minSize)
            assertEquals(2, sizeBetweenViolation.maxSize)
        }

        test("reports a collection larger than the maximum") {
            val violation = violationFor(listOf(1, 2, 3)) { sizeBetween(1, 2) }

            assertIs<SizeBetweenViolation>(violation)
        }

        test("does not report a collection of exactly the minimum size") {
            val violations = violationsFor(listOf(1)) { sizeBetween(1, 2) }

            assertNoViolations(violations)
        }

        test("does not report a collection of exactly the maximum size") {
            val violations = violationsFor(listOf(1, 2)) { sizeBetween(1, 2) }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor(emptyList<Int>()) { sizeBetween(1, 2) }

            assertEquals("Size must be between 1 and 2", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor(emptyList<Int>()) { sizeBetween(1, 2, "custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor(emptyList<Int>(), context) { sizeBetween(1, 2) }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }

    testSuite("exactSize") {
        test("reports a collection smaller than the expected size") {
            val violation = violationFor(listOf(1)) { exactSize(2) }

            val exactSizeViolation = assertIs<ExactSizeViolation>(violation)
            assertEquals(2, exactSizeViolation.expectedSize)
        }

        test("reports a collection larger than the expected size") {
            val violation = violationFor(listOf(1, 2, 3)) { exactSize(2) }

            assertIs<ExactSizeViolation>(violation)
        }

        test("does not report a collection of exactly the expected size") {
            val violations = violationsFor(listOf(1, 2)) { exactSize(2) }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor(listOf(1)) { exactSize(2) }

            assertEquals("Size must be exactly 2", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor(listOf(1)) { exactSize(2, "custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor(listOf(1), context) { exactSize(2) }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }

    testSuite("distinct") {
        test("reports a collection with equal elements") {
            val violation = violationFor(listOf(1, 2, 1)) { distinct() }

            assertIs<DistinctViolation>(violation)
        }

        test("reports equal elements that are different instances") {
            val first = listOf(1)
            val second = listOf(1)

            val violation = violationFor(listOf(first, second)) { distinct() }

            assertIs<DistinctViolation>(violation)
        }

        test("reports a collection with several null elements") {
            val violation = violationFor(listOf<Int?>(null, null)) { distinct() }

            assertIs<DistinctViolation>(violation)
        }

        test("does not report a collection with different elements") {
            val violations = violationsFor(listOf(1, 2, 3)) { distinct() }

            assertNoViolations(violations)
        }

        test("does not report a collection with one null element") {
            val violations = violationsFor(listOf<Int?>(null, 1)) { distinct() }

            assertNoViolations(violations)
        }

        test("does not report an empty collection") {
            val violations = violationsFor(emptyList<Int>()) { distinct() }

            assertNoViolations(violations)
        }

        test("does not report a collection with a single element") {
            val violations = violationsFor(listOf(1)) { distinct() }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor(listOf(1, 1)) { distinct() }

            assertEquals("Must contain only distinct elements", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor(listOf(1, 1)) { distinct("custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor(listOf(1, 1), context) { distinct() }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }
}
