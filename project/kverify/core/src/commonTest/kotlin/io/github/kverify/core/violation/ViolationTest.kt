package io.github.kverify.core.violation

import kotlin.test.Test
import kotlin.test.assertEquals

class ViolationTest {
    @Test
    fun `Violation interface can be implemented`() {
        val reason = "test violation"

        val violation =
            object : Violation {
                override val reason: String = reason
            }

        assertEquals(reason, violation.reason)
    }
}
