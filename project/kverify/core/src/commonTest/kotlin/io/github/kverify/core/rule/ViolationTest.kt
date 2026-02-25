package io.github.kverify.core.rule

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ViolationTest {
    @Test
    fun violationReasonStoresReason() {
        val violation = ViolationReason("something went wrong")

        assertEquals("something went wrong", violation.reason)
    }

    @Test
    fun violationReasonImplementsViolation() {
        val violation = ViolationReason("error")

        assertIs<Violation>(violation)
    }

    @Test
    fun violationFactoryFunctionCreatesViolationReason() {
        val violation = violation("error message")

        assertIs<ViolationReason>(violation)
        assertEquals("error message", violation.reason)
    }

    @Test
    fun violationReasonValueClassEquality() {
        val violation1 = ViolationReason("error")
        val violation2 = ViolationReason("error")
        val violation3 = ViolationReason("other")

        assertEquals(violation1, violation2)
        assertEquals(violation1.hashCode(), violation2.hashCode())
        assertIs<Boolean>(violation1 != violation3)
    }
}
