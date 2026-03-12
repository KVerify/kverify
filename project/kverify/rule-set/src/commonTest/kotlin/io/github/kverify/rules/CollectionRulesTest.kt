package io.github.kverify.rules

import io.github.kverify.core.scope.validateCollecting
import io.github.kverify.core.scope.verify
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
    @Test
    fun minSize_valid() {
        val value = listOf(1, 2, 3)
        val min = 2

        assertTrue(validateCollecting { verify(value).minSize(min) }.isValid)
    }

    @Test
    fun minSize_atBoundary() {
        val value = listOf(1, 2)

        assertTrue(validateCollecting { verify(value).minSize(value.size) }.isValid)
    }

    @Test
    fun minSize_invalid() {
        val value = listOf(1)
        val min = 3

        val result = validateCollecting { verify(value).minSize(min) }

        assertTrue(result.isInvalid)
        assertIs<MinSizeViolation>(result.violations.first())
    }

    @Test
    fun minSize_violationValues() {
        val value = listOf(1)
        val min = 3

        val result = validateCollecting { verify(value).minSize(min) }

        val violation = assertIs<MinSizeViolation>(result.violations.first())
        assertEquals(min, violation.minSizeAllowed)
        assertEquals(value.size, violation.actualSize)
    }

    @Test
    fun maxSize_valid() {
        val value = listOf(1, 2)
        val max = 5

        assertTrue(validateCollecting { verify(value).maxSize(max) }.isValid)
    }

    @Test
    fun maxSize_atBoundary() {
        val value = listOf(1, 2)

        assertTrue(validateCollecting { verify(value).maxSize(value.size) }.isValid)
    }

    @Test
    fun maxSize_invalid() {
        val value = listOf(1, 2, 3, 4)
        val max = 2

        val result = validateCollecting { verify(value).maxSize(max) }

        assertTrue(result.isInvalid)
        assertIs<MaxSizeViolation>(result.violations.first())
    }

    @Test
    fun maxSize_violationValues() {
        val value = listOf(1, 2, 3)
        val max = 1

        val result = validateCollecting { verify(value).maxSize(max) }

        val violation = assertIs<MaxSizeViolation>(result.violations.first())
        assertEquals(max, violation.maxSizeAllowed)
        assertEquals(value.size, violation.actualSize)
    }

    @Test
    fun exactSize_valid() {
        val value = listOf(1, 2, 3)

        assertTrue(validateCollecting { verify(value).exactSize(value.size) }.isValid)
    }

    @Test
    fun exactSize_invalid_tooSmall() {
        val value = listOf(1, 2)
        val size = 3

        val result = validateCollecting { verify(value).exactSize(size) }

        assertTrue(result.isInvalid)
        assertIs<ExactSizeViolation>(result.violations.first())
    }

    @Test
    fun exactSize_invalid_tooBig() {
        val value = listOf(1, 2, 3, 4)
        val size = 3

        assertTrue(validateCollecting { verify(value).exactSize(size) }.isInvalid)
    }

    @Test
    fun exactSize_violationValues() {
        val value = listOf(1, 2)
        val size = 5

        val result = validateCollecting { verify(value).exactSize(size) }

        val violation = assertIs<ExactSizeViolation>(result.violations.first())
        assertEquals(size, violation.expectedSize)
        assertEquals(value.size, violation.actualSize)
    }

    @Test
    fun sizeRange_valid() {
        val value = listOf(1, 2, 3)
        val min = 2
        val max = 5

        assertTrue(validateCollecting { verify(value).sizeRange(min, max) }.isValid)
    }

    @Test
    fun sizeRange_atLowerBoundary() {
        val value = listOf(1, 2)
        val max = 5

        assertTrue(validateCollecting { verify(value).sizeRange(value.size, max) }.isValid)
    }

    @Test
    fun sizeRange_atUpperBoundary() {
        val value = listOf(1, 2, 3, 4, 5)
        val min = 2

        assertTrue(validateCollecting { verify(value).sizeRange(min, value.size) }.isValid)
    }

    @Test
    fun sizeRange_invalid_tooSmall() {
        val value = listOf(1)
        val min = 2
        val max = 5

        val result = validateCollecting { verify(value).sizeRange(min, max) }

        assertTrue(result.isInvalid)
        assertIs<SizeRangeViolation>(result.violations.first())
    }

    @Test
    fun sizeRange_invalid_tooBig() {
        val value = listOf(1, 2, 3, 4, 5, 6)
        val min = 2
        val max = 5

        assertTrue(validateCollecting { verify(value).sizeRange(min, max) }.isInvalid)
    }

    @Test
    fun sizeRange_violationValues() {
        val value = listOf(1)
        val min = 2
        val max = 5

        val result = validateCollecting { verify(value).sizeRange(min, max) }

        val violation = assertIs<SizeRangeViolation>(result.violations.first())
        assertEquals(min, violation.minSizeAllowed)
        assertEquals(max, violation.maxSizeAllowed)
        assertEquals(value.size, violation.actualSize)
    }

    @Test
    fun distinct_valid() {
        val value = listOf(1, 2, 3)

        assertTrue(validateCollecting { verify(value).distinct() }.isValid)
    }

    @Test
    fun distinct_emptyList() {
        assertTrue(validateCollecting { verify(emptyList<Int>()).distinct() }.isValid)
    }

    @Test
    fun distinct_invalid() {
        val value = listOf(1, 1, 2)

        val result = validateCollecting { verify(value).distinct() }

        assertTrue(result.isInvalid)
        assertIs<DistinctViolation>(result.violations.first())
    }

    @Test
    fun distinct_violationValues() {
        val value = listOf(1, 1, 2, 2)

        val result = validateCollecting { verify(value).distinct() }

        val violation = assertIs<DistinctViolation>(result.violations.first())
        assertEquals(value.size, violation.actualSize)
        assertEquals(value.toSet().size, violation.distinctSize)
    }
}
