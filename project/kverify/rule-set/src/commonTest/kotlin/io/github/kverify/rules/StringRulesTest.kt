package io.github.kverify.rules

import io.github.kverify.core.scope.verify
import io.github.kverify.core.scope.verifyCollecting
import io.github.kverify.violations.ExactLengthViolation
import io.github.kverify.violations.LengthRangeViolation
import io.github.kverify.violations.MaxLengthViolation
import io.github.kverify.violations.MinLengthViolation
import io.github.kverify.violations.NotBlankViolation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class StringRulesTest {
    // notBlank

    @Test
    fun notBlankPassesForNonBlank() {
        val violations =
            verifyCollecting {
                verify("hello").notBlank()
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun notBlankFailsForEmpty() {
        val violations =
            verifyCollecting {
                verify("").notBlank()
            }.violations
        assertEquals(1, violations.size)
        assertIs<NotBlankViolation>(violations[0])
    }

    @Test
    fun notBlankFailsForWhitespace() {
        val violations =
            verifyCollecting {
                verify("   ").notBlank()
            }.violations
        assertEquals(1, violations.size)
        assertIs<NotBlankViolation>(violations[0])
    }

    @Test
    fun notBlankUsesCustomReason() {
        val violations =
            verifyCollecting {
                verify("").notBlank(reason = "Name is required")
            }.violations
        assertEquals("Name is required", violations[0].reason)
    }

    // minLength

    @Test
    fun minLengthPassesWhenMet() {
        val violations =
            verifyCollecting {
                verify("abc").minLength(3)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun minLengthPassesWhenExceeded() {
        val violations =
            verifyCollecting {
                verify("abcd").minLength(3)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun minLengthFailsWhenTooShort() {
        val violations =
            verifyCollecting {
                verify("ab").minLength(3)
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<MinLengthViolation>(violations[0])
        assertEquals(3, violation.minLengthAllowed)
        assertEquals(2, violation.actualLength)
    }

    // maxLength

    @Test
    fun maxLengthPassesWhenMet() {
        val violations =
            verifyCollecting {
                verify("abc").maxLength(3)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun maxLengthPassesWhenUnder() {
        val violations =
            verifyCollecting {
                verify("ab").maxLength(3)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun maxLengthFailsWhenExceeded() {
        val violations =
            verifyCollecting {
                verify("abcd").maxLength(3)
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<MaxLengthViolation>(violations[0])
        assertEquals(3, violation.maxLengthAllowed)
        assertEquals(4, violation.actualLength)
    }

    // exactLength

    @Test
    fun exactLengthPassesWhenExact() {
        val violations =
            verifyCollecting {
                verify("abc").exactLength(3)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun exactLengthFailsWhenTooShort() {
        val violations =
            verifyCollecting {
                verify("ab").exactLength(3)
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<ExactLengthViolation>(violations[0])
        assertEquals(3, violation.expectedLength)
        assertEquals(2, violation.actualLength)
    }

    @Test
    fun exactLengthFailsWhenTooLong() {
        val violations =
            verifyCollecting {
                verify("abcd").exactLength(3)
            }.violations
        assertEquals(1, violations.size)
        assertIs<ExactLengthViolation>(violations[0])
    }

    // lengthRange

    @Test
    fun lengthRangePassesWithinRange() {
        val violations =
            verifyCollecting {
                verify("abc").lengthRange(2, 5)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun lengthRangePassesAtLowerBound() {
        val violations =
            verifyCollecting {
                verify("ab").lengthRange(2, 5)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun lengthRangePassesAtUpperBound() {
        val violations =
            verifyCollecting {
                verify("abcde").lengthRange(2, 5)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun lengthRangeFailsBelowRange() {
        val violations =
            verifyCollecting {
                verify("a").lengthRange(2, 5)
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<LengthRangeViolation>(violations[0])
        assertEquals(2, violation.minLengthAllowed)
        assertEquals(5, violation.maxLengthAllowed)
        assertEquals(1, violation.actualLength)
    }

    @Test
    fun lengthRangeFailsAboveRange() {
        val violations =
            verifyCollecting {
                verify("abcdef").lengthRange(2, 5)
            }.violations
        assertEquals(1, violations.size)
        assertIs<LengthRangeViolation>(violations[0])
    }

    // chaining

    @Test
    fun chainingMultipleRulesCollectsAllViolations() {
        val violations =
            verifyCollecting {
                verify("").notBlank().minLength(5)
            }.violations
        assertEquals(2, violations.size)
        assertIs<NotBlankViolation>(violations[0])
        assertIs<MinLengthViolation>(violations[1])
    }
}
