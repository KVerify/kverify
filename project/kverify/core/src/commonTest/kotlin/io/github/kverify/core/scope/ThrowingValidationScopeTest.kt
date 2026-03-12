package io.github.kverify.core.scope

import io.github.kverify.core.exception.ViolationException
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

class ThrowingValidationScopeTest {
    @Test
    fun returnsBlockResult() {
        val expected = "computed value"

        val result = validateThrowing { expected }

        assertEquals(expected, result)
    }

    @Test
    fun throwsOnViolation() {
        assertFailsWith<ViolationException> {
            validateThrowing { enforce { violation("error") } }
        }
    }

    @Test
    fun thrownException_containsViolation() {
        val expected = violation("specific error")

        val ex =
            assertFailsWith<ViolationException> {
                validateThrowing { enforce { expected } }
            }

        assertEquals(expected, ex.violation)
    }

    @Test
    fun stopsAtFirstViolation() {
        var secondRuleReached = false

        runCatching {
            validateThrowing {
                enforce { violation("first") }
                secondRuleReached = true
            }
        }

        assertFalse(secondRuleReached)
    }

    @Test
    fun failIf_throwsWhenTrue() {
        val expected = violation("condition met")

        val ex =
            assertFailsWith<ViolationException> {
                validateThrowing { failIf({ true }) { expected } }
            }

        assertEquals(expected, ex.violation)
    }

    @Test
    fun failIf_doesNotThrowWhenFalse() {
        validateThrowing { failIf({ false }) { violation("unreachable") } }
    }

    @Test
    fun nullReturningRule_doesNotThrow() {
        validateThrowing { enforce { null } }
    }
}
