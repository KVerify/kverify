package io.github.kverify.core.rule.predicate

import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals

class ViolationFactoryTest {
    @Test
    fun violationFactoryIsFunctionalInterface() {
        val violation = violation("test error")
        val factory = ViolationFactory<String> { violation }

        val result = factory.createViolation("test")

        assertEquals(violation, result)
    }

    @Test
    fun violationFactoryCanAccessValue() {
        val factory =
            ViolationFactory<String> { value ->
                violation("Error for: $value")
            }

        val result = factory.createViolation("test")

        assertEquals("Error for: test", result.reason)
    }

    @Test
    fun violationFactoryWorksWithAnyType() {
        val violation = violation("error")
        val factory = ViolationFactory<Any?> { violation }

        val result = factory.createViolation(null)

        assertEquals(violation, result)
    }
}
