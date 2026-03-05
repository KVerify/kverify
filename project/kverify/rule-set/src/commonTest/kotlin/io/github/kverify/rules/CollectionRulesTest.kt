package io.github.kverify.rules

import io.github.kverify.core.scope.verify
import io.github.kverify.core.scope.verifyCollecting
import io.github.kverify.violations.DistinctViolation
import io.github.kverify.violations.ExactSizeViolation
import io.github.kverify.violations.MaxSizeViolation
import io.github.kverify.violations.MinSizeViolation
import io.github.kverify.violations.SizeRangeViolation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class CollectionRulesTest {
    // minSize

    @Test
    fun minSizePassesWhenMet() {
        val violations =
            verifyCollecting {
                verify(listOf(1, 2, 3)).minSize(3)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun minSizePassesWhenExceeded() {
        val violations =
            verifyCollecting {
                verify(listOf(1, 2, 3, 4)).minSize(3)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun minSizeFailsWhenTooSmall() {
        val violations =
            verifyCollecting {
                verify(listOf(1, 2)).minSize(3)
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<MinSizeViolation>(violations[0])
        assertEquals(3, violation.minSizeAllowed)
        assertEquals(2, violation.actualSize)
    }

    @Test
    fun minSizeFailsForEmptyCollection() {
        val violations =
            verifyCollecting {
                verify(emptyList<Int>()).minSize(1)
            }.violations
        assertEquals(1, violations.size)
        assertIs<MinSizeViolation>(violations[0])
    }

    // maxSize

    @Test
    fun maxSizePassesWhenMet() {
        val violations =
            verifyCollecting {
                verify(listOf(1, 2, 3)).maxSize(3)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun maxSizePassesWhenUnder() {
        val violations =
            verifyCollecting {
                verify(listOf(1, 2)).maxSize(3)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun maxSizeFailsWhenExceeded() {
        val violations =
            verifyCollecting {
                verify(listOf(1, 2, 3, 4)).maxSize(3)
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<MaxSizeViolation>(violations[0])
        assertEquals(3, violation.maxSizeAllowed)
        assertEquals(4, violation.actualSize)
    }

    // exactSize

    @Test
    fun exactSizePassesWhenExact() {
        val violations =
            verifyCollecting {
                verify(listOf(1, 2, 3)).exactSize(3)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun exactSizeFailsWhenTooSmall() {
        val violations =
            verifyCollecting {
                verify(listOf(1, 2)).exactSize(3)
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<ExactSizeViolation>(violations[0])
        assertEquals(3, violation.expectedSize)
        assertEquals(2, violation.actualSize)
    }

    @Test
    fun exactSizeFailsWhenTooLarge() {
        val violations =
            verifyCollecting {
                verify(listOf(1, 2, 3, 4)).exactSize(3)
            }.violations
        assertEquals(1, violations.size)
        assertIs<ExactSizeViolation>(violations[0])
    }

    // sizeRange

    @Test
    fun sizeRangePassesWithinRange() {
        val violations =
            verifyCollecting {
                verify(listOf(1, 2, 3)).sizeRange(2, 5)
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun sizeRangePassesAtBounds() {
        val lower =
            verifyCollecting {
                verify(listOf(1, 2)).sizeRange(2, 5)
            }.violations
        val upper =
            verifyCollecting {
                verify(listOf(1, 2, 3, 4, 5)).sizeRange(2, 5)
            }.violations
        assertTrue(lower.isEmpty())
        assertTrue(upper.isEmpty())
    }

    @Test
    fun sizeRangeFailsOutsideRange() {
        val violations =
            verifyCollecting {
                verify(listOf(1)).sizeRange(2, 5)
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<SizeRangeViolation>(violations[0])
        assertEquals(2, violation.minSizeAllowed)
        assertEquals(5, violation.maxSizeAllowed)
        assertEquals(1, violation.actualSize)
    }

    // distinct

    @Test
    fun distinctPassesForUniqueElements() {
        val violations =
            verifyCollecting {
                verify(listOf(1, 2, 3)).distinct()
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun distinctPassesForEmptyCollection() {
        val violations =
            verifyCollecting {
                verify(emptyList<Int>()).distinct()
            }.violations
        assertTrue(violations.isEmpty())
    }

    @Test
    fun distinctFailsForDuplicates() {
        val violations =
            verifyCollecting {
                verify(listOf(1, 2, 2, 3, 3)).distinct()
            }.violations
        assertEquals(1, violations.size)
        val violation = assertIs<DistinctViolation>(violations[0])
        assertEquals(5, violation.actualSize)
        assertEquals(3, violation.distinctSize)
    }

    // chaining

    @Test
    fun chainingMultipleRulesCollectsAllViolations() {
        val violations =
            verifyCollecting {
                verify(listOf(1, 1)).minSize(5).distinct()
            }.violations
        assertEquals(2, violations.size)
        assertIs<MinSizeViolation>(violations[0])
        assertIs<DistinctViolation>(violations[1])
    }
}
