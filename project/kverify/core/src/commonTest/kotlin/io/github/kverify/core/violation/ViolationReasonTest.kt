package io.github.kverify.core.violation

import kotlin.test.Test
import kotlin.test.assertEquals

class ViolationReasonTest {
    @Test
    fun `ViolationReason can be created using constructor`() {
        val reason = "test violation reason"
        val violationReason = ViolationReason(reason)
        assertEquals(reason, violationReason.reason)
    }

    @Test
    fun `ViolationReason toString returns correct string`() {
        val reason = "test violation reason"
        val violationReason = ViolationReason(reason)
        assertEquals("ViolationReason(reason=$reason)", violationReason.toString())
    }

    @Test
    fun `ViolationReason can be created using asViolationReason function`() {
        val reason = "test violation reason"
        val violationReason = reason.asViolationReason()
        assertEquals(reason, violationReason.reason)
    }

    @Test
    fun `ViolationReason can be created using violation function`() {
        val reason = "test violation reason"
        val violationReason = violation(reason)
        assertEquals(reason, violationReason.reason)
    }
}
