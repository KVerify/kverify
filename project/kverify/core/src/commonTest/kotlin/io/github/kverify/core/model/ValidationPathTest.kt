package io.github.kverify.core.model

import de.infix.testBalloon.framework.core.testSuite
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

val ValidationPathTest by testSuite {
    testSuite("Empty") {
        test("has no segments") {
            val result = ValidationPath.Empty

            assertTrue(result.segments.isEmpty())
        }
    }

    testSuite("equals") {
        test("paths with equal segments are equal") {
            val left = path(NamePathSegment("a"), IndexPathSegment(0))
            val right = path(NamePathSegment("a"), IndexPathSegment(0))

            assertEquals(left, right)
        }

        test("paths with different segments are not equal") {
            val left = path(NamePathSegment("a"))
            val right = path(NamePathSegment("b"))

            assertNotEquals(left, right)
        }

        test("paths with the same segments in a different order are not equal") {
            val left = path(NamePathSegment("a"), IndexPathSegment(0))
            val right = path(IndexPathSegment(0), NamePathSegment("a"))

            assertNotEquals(left, right)
        }

        test("a path is equal to itself") {
            val validationPath = path(NamePathSegment("a"))

            assertEquals(validationPath, validationPath)
        }

        test("a path is not equal to an object of another type") {
            val validationPath = path(NamePathSegment("a"))

            assertNotEquals<Any>(validationPath, "a")
        }
    }

    testSuite("hashCode") {
        test("paths with equal segments have equal hash codes") {
            val left = path(NamePathSegment("a"), IndexPathSegment(0))
            val right = path(NamePathSegment("a"), IndexPathSegment(0))

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows name segments in quotes") {
            val validationPath = path(NamePathSegment("a"), NamePathSegment("b"))

            val result = validationPath.toString()

            assertEquals("ValidationPath(\"a\", \"b\")", result)
        }

        test("shows index segments as numbers") {
            val validationPath = path(IndexPathSegment(0), IndexPathSegment(12))

            val result = validationPath.toString()

            assertEquals("ValidationPath(0, 12)", result)
        }

        test("shows the segments in order") {
            val validationPath = path(NamePathSegment("a"), IndexPathSegment(0), NamePathSegment("b"))

            val result = validationPath.toString()

            assertEquals("ValidationPath(\"a\", 0, \"b\")", result)
        }

        test("shows no segments for the empty path") {
            val result = ValidationPath.Empty.toString()

            assertEquals("ValidationPath()", result)
        }
    }
}

private fun path(vararg segments: ValidationPathSegment): ValidationPath = ValidationPath(segments.asList())
