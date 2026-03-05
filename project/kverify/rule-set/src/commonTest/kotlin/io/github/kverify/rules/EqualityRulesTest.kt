package io.github.kverify.rules

import io.github.kverify.core.scope.verify
import io.github.kverify.core.scope.verifyCollecting
import io.github.kverify.violations.EqualToViolation
import io.github.kverify.violations.NoneOfViolation
import io.github.kverify.violations.NotEqualToViolation
import io.github.kverify.violations.OneOfViolation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class EqualityRulesTest {
    // equalTo

    @Test
    fun equalToPassesWhenEqual() {
        val violations =
            verifyCollecting {
                verify("hello").equalTo("hello")
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun equalToFailsWhenNotEqual() {
        val violations =
            verifyCollecting {
                verify("hello").equalTo("world")
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<EqualToViolation<String>>(violations[0])
        assertEquals("world", violation.expected)
        assertEquals("hello", violation.actual)
    }

    // notEqualTo

    @Test
    fun notEqualToPassesWhenDifferent() {
        val violations =
            verifyCollecting {
                verify("hello").notEqualTo("world")
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun notEqualToFailsWhenEqual() {
        val violations =
            verifyCollecting {
                verify("hello").notEqualTo("hello")
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<NotEqualToViolation<String>>(violations[0])
        assertEquals("hello", violation.forbidden)
    }

    // oneOf

    @Test
    fun oneOfPassesWhenInSet() {
        val violations =
            verifyCollecting {
                verify("b").oneOf(setOf("a", "b", "c"))
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun oneOfFailsWhenNotInSet() {
        val violations =
            verifyCollecting {
                verify("d").oneOf(setOf("a", "b", "c"))
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<OneOfViolation<String>>(violations[0])
        assertEquals(setOf("a", "b", "c"), violation.allowed)
        assertEquals("d", violation.actual)
    }

    @Test
    fun oneOfFailsForEmptySet() {
        val violations =
            verifyCollecting {
                verify("a").oneOf(emptySet())
            }.violations
        assertEquals(1, violations.size)
        assertIs<OneOfViolation<String>>(violations[0])
    }

    // noneOf

    @Test
    fun noneOfPassesWhenNotInSet() {
        val violations =
            verifyCollecting {
                verify("d").noneOf(setOf("a", "b", "c"))
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun noneOfFailsWhenInSet() {
        val violations =
            verifyCollecting {
                verify("b").noneOf(setOf("a", "b", "c"))
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<NoneOfViolation<String>>(violations[0])
        assertEquals(setOf("a", "b", "c"), violation.forbidden)
        assertEquals("b", violation.actual)
    }

    @Test
    fun noneOfPassesForEmptySet() {
        val violations =
            verifyCollecting {
                verify("a").noneOf(emptySet())
            }.violations
        assertTrue(violations.isEmpty())
    }

    // custom reason

    @Test
    fun customReasonIsUsed() {
        val violations =
            verifyCollecting {
                verify("x").equalTo("y", reason = "Must match")
            }.violations
        assertEquals("Must match", violations[0].reason)
    }

    // works with different types

    @Test
    fun worksWithInts() {
        val violations =
            verifyCollecting {
                verify(42).equalTo(42)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun worksWithNullable() {
        val violations =
            verifyCollecting {
                verify(null).equalTo(null)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun worksWithNullableNotEqual() {
        val violations =
            verifyCollecting {
                verify<String?>(null).notEqualTo("hello")
            }.violations
        assertTrue(violations.isEmpty())
    }
}
