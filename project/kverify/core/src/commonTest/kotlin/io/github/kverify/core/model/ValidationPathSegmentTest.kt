package io.github.kverify.core.model

import de.infix.testBalloon.framework.core.testSuite
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val NamePathSegmentTest by testSuite {
    testSuite("equals") {
        test("segments with equal names are equal") {
            val left = NamePathSegment("a")
            val right = NamePathSegment("a")

            assertEquals(left, right)
        }

        test("segments with different names are not equal") {
            val left = NamePathSegment("a")
            val right = NamePathSegment("b")

            assertNotEquals(left, right)
        }

        test("a segment is equal to itself") {
            val segment = NamePathSegment("a")

            assertEquals(segment, segment)
        }

        test("a name segment is not equal to an index segment with the same text") {
            val name = NamePathSegment("0")
            val index = IndexPathSegment(0)

            assertNotEquals<ValidationPathSegment>(name, index)
        }

        test("a segment is not equal to an object of another type") {
            val segment = NamePathSegment("a")

            assertNotEquals<Any>(segment, "a")
        }
    }

    testSuite("hashCode") {
        test("segments with equal names have equal hash codes") {
            val left = NamePathSegment("a")
            val right = NamePathSegment("a")

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows the name") {
            val segment = NamePathSegment("a")

            val result = segment.toString()

            assertEquals("NamePathSegment(name=a)", result)
        }
    }
}

val IndexPathSegmentTest by testSuite {
    testSuite("equals") {
        test("segments with equal indexes are equal") {
            val left = IndexPathSegment(1)
            val right = IndexPathSegment(1)

            assertEquals(left, right)
        }

        test("segments with different indexes are not equal") {
            val left = IndexPathSegment(1)
            val right = IndexPathSegment(2)

            assertNotEquals(left, right)
        }

        test("a segment is equal to itself") {
            val segment = IndexPathSegment(1)

            assertEquals(segment, segment)
        }

        test("an index segment is not equal to a name segment with the same text") {
            val index = IndexPathSegment(0)
            val name = NamePathSegment("0")

            assertNotEquals<ValidationPathSegment>(index, name)
        }

        test("a segment is not equal to an object of another type") {
            val segment = IndexPathSegment(1)

            assertNotEquals<Any>(segment, 1)
        }
    }

    testSuite("hashCode") {
        test("segments with equal indexes have equal hash codes") {
            val left = IndexPathSegment(1)
            val right = IndexPathSegment(1)

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows the index") {
            val segment = IndexPathSegment(1)

            val result = segment.toString()

            assertEquals("IndexPathSegment(index=1)", result)
        }
    }
}
