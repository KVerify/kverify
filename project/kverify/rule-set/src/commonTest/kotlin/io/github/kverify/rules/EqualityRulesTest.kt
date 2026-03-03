package io.github.kverify.rules

import io.github.kverify.core.scope.verify
import io.github.kverify.core.scope.verifyWithCollecting
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
        val violations = verifyWithCollecting {
            verify("hello").equalTo("hello")
        }
        assertTrue(violations.isEmpty())
    }

    @Test
    fun equalToFailsWhenNotEqual() {
        val violations = verifyWithCollecting {
            verify("hello").equalTo("world")
        }
        assertEquals(1, violations.size)
        val violation = assertIs<EqualToViolation<String>>(violations[0])
        assertEquals("world", violation.expected)
        assertEquals("hello", violation.actual)
    }

    // notEqualTo

    @Test
    fun notEqualToPassesWhenDifferent() {
        val violations = verifyWithCollecting {
            verify("hello").notEqualTo("world")
        }
        assertTrue(violations.isEmpty())
    }

    @Test
    fun notEqualToFailsWhenEqual() {
        val violations = verifyWithCollecting {
            verify("hello").notEqualTo("hello")
        }
        assertEquals(1, violations.size)
        val violation = assertIs<NotEqualToViolation<String>>(violations[0])
        assertEquals("hello", violation.forbidden)
    }

    // oneOf

    @Test
    fun oneOfPassesWhenInSet() {
        val violations = verifyWithCollecting {
            verify("b").oneOf(setOf("a", "b", "c"))
        }
        assertTrue(violations.isEmpty())
    }

    @Test
    fun oneOfFailsWhenNotInSet() {
        val violations = verifyWithCollecting {
            verify("d").oneOf(setOf("a", "b", "c"))
        }
        assertEquals(1, violations.size)
        val violation = assertIs<OneOfViolation<String>>(violations[0])
        assertEquals(setOf("a", "b", "c"), violation.allowed)
        assertEquals("d", violation.actual)
    }

    @Test
    fun oneOfFailsForEmptySet() {
        val violations = verifyWithCollecting {
            verify("a").oneOf(emptySet())
        }
        assertEquals(1, violations.size)
        assertIs<OneOfViolation<String>>(violations[0])
    }

    // noneOf

    @Test
    fun noneOfPassesWhenNotInSet() {
        val violations = verifyWithCollecting {
            verify("d").noneOf(setOf("a", "b", "c"))
        }
        assertTrue(violations.isEmpty())
    }

    @Test
    fun noneOfFailsWhenInSet() {
        val violations = verifyWithCollecting {
            verify("b").noneOf(setOf("a", "b", "c"))
        }
        assertEquals(1, violations.size)
        val violation = assertIs<NoneOfViolation<String>>(violations[0])
        assertEquals(setOf("a", "b", "c"), violation.forbidden)
        assertEquals("b", violation.actual)
    }

    @Test
    fun noneOfPassesForEmptySet() {
        val violations = verifyWithCollecting {
            verify("a").noneOf(emptySet())
        }
        assertTrue(violations.isEmpty())
    }

    // custom reason

    @Test
    fun customReasonIsUsed() {
        val violations = verifyWithCollecting {
            verify("x").equalTo("y", reason = "Must match")
        }
        assertEquals("Must match", violations[0].reason)
    }

    // works with different types

    @Test
    fun worksWithInts() {
        val violations = verifyWithCollecting {
            verify(42).equalTo(42)
        }
        assertTrue(violations.isEmpty())
    }

    @Test
    fun worksWithNullable() {
        val violations = verifyWithCollecting {
            verify(null).equalTo(null)
        }
        assertTrue(violations.isEmpty())
    }

    @Test
    fun worksWithNullableNotEqual() {
        val violations = verifyWithCollecting {
            verify<String?>(null).notEqualTo("hello")
        }
        assertTrue(violations.isEmpty())
    }
}
