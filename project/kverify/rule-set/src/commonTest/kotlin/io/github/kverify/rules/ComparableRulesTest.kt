package io.github.kverify.rules

import io.github.kverify.core.scope.verify
import io.github.kverify.core.scope.verifyWithCollecting
import io.github.kverify.violations.AtLeastViolation
import io.github.kverify.violations.AtMostViolation
import io.github.kverify.violations.BetweenViolation
import io.github.kverify.violations.GreaterThanViolation
import io.github.kverify.violations.LessThanViolation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ComparableRulesTest {
    // atLeast

    @Test
    fun atLeastPassesWhenEqual() {
        val violations =
            verifyWithCollecting {
                verify(5).atLeast(5)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun atLeastPassesWhenGreater() {
        val violations =
            verifyWithCollecting {
                verify(6).atLeast(5)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun atLeastFailsWhenLess() {
        val violations =
            verifyWithCollecting {
                verify(4).atLeast(5)
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<AtLeastViolation<Int>>(violations[0])
        assertEquals(5, violation.minAllowed)
        assertEquals(4, violation.actual)
    }

    // atMost

    @Test
    fun atMostPassesWhenEqual() {
        val violations =
            verifyWithCollecting {
                verify(5).atMost(5)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun atMostPassesWhenLess() {
        val violations =
            verifyWithCollecting {
                verify(4).atMost(5)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun atMostFailsWhenGreater() {
        val violations =
            verifyWithCollecting {
                verify(6).atMost(5)
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<AtMostViolation<Int>>(violations[0])
        assertEquals(5, violation.maxAllowed)
        assertEquals(6, violation.actual)
    }

    // between

    @Test
    fun betweenPassesWithinRange() {
        val violations =
            verifyWithCollecting {
                verify(5).between(1, 10)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun betweenPassesAtLowerBound() {
        val violations =
            verifyWithCollecting {
                verify(1).between(1, 10)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun betweenPassesAtUpperBound() {
        val violations =
            verifyWithCollecting {
                verify(10).between(1, 10)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun betweenFailsBelowRange() {
        val violations =
            verifyWithCollecting {
                verify(0).between(1, 10)
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<BetweenViolation<Int>>(violations[0])
        assertEquals(1, violation.min)
        assertEquals(10, violation.max)
        assertEquals(0, violation.actual)
    }

    @Test
    fun betweenFailsAboveRange() {
        val violations =
            verifyWithCollecting {
                verify(11).between(1, 10)
            }.violations
        assertEquals(1, violations.size)
        assertIs<BetweenViolation<Int>>(violations[0])
    }

    // greaterThan

    @Test
    fun greaterThanPassesWhenGreater() {
        val violations =
            verifyWithCollecting {
                verify(6).greaterThan(5)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun greaterThanFailsWhenEqual() {
        val violations =
            verifyWithCollecting {
                verify(5).greaterThan(5)
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<GreaterThanViolation<Int>>(violations[0])
        assertEquals(5, violation.minExclusive)
        assertEquals(5, violation.actual)
    }

    @Test
    fun greaterThanFailsWhenLess() {
        val violations =
            verifyWithCollecting {
                verify(4).greaterThan(5)
            }.violations
        assertEquals(1, violations.size)
        assertIs<GreaterThanViolation<Int>>(violations[0])
    }

    // lessThan

    @Test
    fun lessThanPassesWhenLess() {
        val violations =
            verifyWithCollecting {
                verify(4).lessThan(5)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun lessThanFailsWhenEqual() {
        val violations =
            verifyWithCollecting {
                verify(5).lessThan(5)
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<LessThanViolation<Int>>(violations[0])
        assertEquals(5, violation.maxExclusive)
        assertEquals(5, violation.actual)
    }

    @Test
    fun lessThanFailsWhenGreater() {
        val violations =
            verifyWithCollecting {
                verify(6).lessThan(5)
            }.violations
        assertEquals(1, violations.size)
        assertIs<LessThanViolation<Int>>(violations[0])
    }

    // custom reason

    @Test
    fun customReasonIsUsed() {
        val violations =
            verifyWithCollecting {
                verify(0).atLeast(1, reason = "Must be positive")
            }.violations
        assertEquals("Must be positive", violations[0].reason)
    }

    // works with different comparable types

    @Test
    fun worksWithDouble() {
        val violations =
            verifyWithCollecting {
                verify(3.14).between(1.0, 5.0)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun worksWithString() {
        val violations =
            verifyWithCollecting {
                verify("banana").between("apple", "cherry")
            }.violations
        assertTrue(violations.isEmpty())
    }
}
