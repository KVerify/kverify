package io.github.kverify.core.violation

import kotlin.test.Test
import kotlin.test.assertEquals

class ViolationReasonTest {
    @Test
    fun violationReasonHasCorrectReason() {
        val reason = "test error"
        val violation = ViolationReason(reason)

        assertEquals(reason, violation.reason)
    }

    @Test
    fun asViolationReasonCreatesViolationReason() {
        val reason = "test error"
        val violation = reason.asViolationReason()

        assertEquals(reason, violation.reason)
    }

    @Test
    fun violationFactoryFunctionCreatesViolationReason() {
        val reason = "test error"
        val viol = violation(reason)

        assertEquals(reason, viol.reason)
    }

    @Test
    fun violationReasonImplementsViolation() {
        val violation: Violation = ViolationReason("test")

        assertEquals("test", violation.reason)
    }
}
