package io.github.kverify.core.violation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ViolationTest {
    @Test
    fun violationFactoryStoresReason() {
        val reason = "value must not be null"

        val result = violation(reason)

        assertEquals(reason, result.reason)
    }

    @Test
    fun violationFactoryToStringContainsReason() {
        val reason = "must be positive"

        val result = violation(reason).toString()

        assertEquals("Violation(reason=$reason)", result)
    }

    @Test
    fun twoViolationsWithSameReasonAreEqual() {
        val reason = "same reason"

        assertEquals(violation(reason), violation(reason))
    }

    @Test
    fun twoViolationsWithDifferentReasonsAreNotEqual() {
        assertNotEquals(violation("first"), violation("second"))
    }

    @Test
    fun twoViolationsWithSameReasonHaveSameHashCode() {
        val reason = "same reason"

        assertEquals(violation(reason).hashCode(), violation(reason).hashCode())
    }
}
