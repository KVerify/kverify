package io.github.kverify.core.violation

import kotlin.test.Test
import kotlin.test.assertEquals

class ViolationReasonTest {
    @Test
    fun violationReasonCanBeCreatedUsingConstructor() {
        val reason = "test violation reason"
        val violationReason = ViolationReason(reason)
        assertEquals(reason, violationReason.reason)
    }

    @Test
    fun violationReasonToStringReturnsCorrectString() {
        val reason = "test violation reason"
        val violationReason = ViolationReason(reason)
        assertEquals("ViolationReason(reason=$reason)", violationReason.toString())
    }

    @Test
    fun violationReasonCanBeCreatedUsingAsViolationReasonFunction() {
        val reason = "test violation reason"
        val violationReason = reason.asViolationReason()
        assertEquals(reason, violationReason.reason)
    }

    @Test
    fun violationReasonCanBeCreatedUsingViolationFunction() {
        val reason = "test violation reason"
        val violationReason = violation(reason)
        assertEquals(reason, violationReason.reason)
    }
}
