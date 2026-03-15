package io.github.kverify.core.violation

import kotlin.test.Test
import kotlin.test.assertEquals

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
}
