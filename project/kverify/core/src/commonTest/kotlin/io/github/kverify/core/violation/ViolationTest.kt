package io.github.kverify.core.violation

import kotlin.test.Test
import kotlin.test.assertEquals

class ViolationTest {
    @Test
    fun violationInterfaceCanBeImplemented() {
        val reason = "test violation"

        val violation =
            object : Violation {
                override val reason: String = reason
            }

        assertEquals(reason, violation.reason)
    }
}
