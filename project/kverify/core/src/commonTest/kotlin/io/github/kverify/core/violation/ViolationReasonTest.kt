package io.github.kverify.core.violation

import de.infix.testBalloon.framework.core.testSuite
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

val ViolationReasonTest by testSuite {
    testSuite("reason") {
        test("holds the given reason") {
            val violation = ViolationReason("too short")

            assertEquals("too short", violation.reason)
        }
    }

    testSuite("equals") {
        test("violations with equal reasons are equal") {
            val left = ViolationReason("a")
            val right = ViolationReason("a")

            assertEquals(left, right)
        }

        test("violations with different reasons are not equal") {
            val left = ViolationReason("a")
            val right = ViolationReason("b")

            assertNotEquals(left, right)
        }

        test("a violation is equal to itself") {
            val violation = ViolationReason("a")

            assertEquals(violation, violation)
        }

        test("a violation is not equal to an object of another type") {
            val violation = ViolationReason("a")

            assertNotEquals<Any>(violation, "a")
        }
    }

    testSuite("hashCode") {
        test("violations with equal reasons have equal hash codes") {
            val left = ViolationReason("a")
            val right = ViolationReason("a")

            assertEquals(left.hashCode(), right.hashCode())
        }
    }

    testSuite("toString") {
        test("shows the reason") {
            val violation = ViolationReason("a")

            val result = violation.toString()

            assertEquals("ViolationReason(reason=a)", result)
        }
    }
}
