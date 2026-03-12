package io.github.kverify.rules

import io.github.kverify.core.scope.validateCollecting
import io.github.kverify.core.scope.verify
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
    @Test
    fun atLeast_valid() {
        val value = 5
        val min = 3

        assertTrue(validateCollecting { verify(value).atLeast(min) }.isValid)
    }

    @Test
    fun atLeast_atBoundary() {
        val value = 5

        assertTrue(validateCollecting { verify(value).atLeast(value) }.isValid)
    }

    @Test
    fun atLeast_invalid() {
        val value = 2
        val min = 5

        val result = validateCollecting { verify(value).atLeast(min) }

        assertTrue(result.isInvalid)
        assertIs<AtLeastViolation<*>>(result.violations.first())
    }

    @Test
    fun atLeast_violationValues() {
        val value = 2
        val min = 5

        val result = validateCollecting { verify(value).atLeast(min) }

        val violation = assertIs<AtLeastViolation<*>>(result.violations.first())
        assertEquals(min, violation.minAllowed)
        assertEquals(value, violation.actual)
    }

    @Test
    fun atMost_valid() {
        val value = 3
        val max = 5

        assertTrue(validateCollecting { verify(value).atMost(max) }.isValid)
    }

    @Test
    fun atMost_atBoundary() {
        val value = 5

        assertTrue(validateCollecting { verify(value).atMost(value) }.isValid)
    }

    @Test
    fun atMost_invalid() {
        val value = 6
        val max = 5

        val result = validateCollecting { verify(value).atMost(max) }

        assertTrue(result.isInvalid)
        assertIs<AtMostViolation<*>>(result.violations.first())
    }

    @Test
    fun atMost_violationValues() {
        val value = 6
        val max = 5

        val result = validateCollecting { verify(value).atMost(max) }

        val violation = assertIs<AtMostViolation<*>>(result.violations.first())
        assertEquals(max, violation.maxAllowed)
        assertEquals(value, violation.actual)
    }

    @Test
    fun between_valid() {
        val value = 3
        val min = 1
        val max = 5

        assertTrue(validateCollecting { verify(value).between(min, max) }.isValid)
    }

    @Test
    fun between_atLowerBoundary() {
        val value = 1
        val max = 5

        assertTrue(validateCollecting { verify(value).between(value, max) }.isValid)
    }

    @Test
    fun between_atUpperBoundary() {
        val value = 5
        val min = 1

        assertTrue(validateCollecting { verify(value).between(min, value) }.isValid)
    }

    @Test
    fun between_invalid_tooSmall() {
        val value = 0
        val min = 1
        val max = 5

        val result = validateCollecting { verify(value).between(min, max) }

        assertTrue(result.isInvalid)
        assertIs<BetweenViolation<*>>(result.violations.first())
    }

    @Test
    fun between_invalid_tooBig() {
        val value = 6
        val min = 1
        val max = 5

        assertTrue(validateCollecting { verify(value).between(min, max) }.isInvalid)
    }

    @Test
    fun between_violationValues() {
        val value = 0
        val min = 1
        val max = 5

        val result = validateCollecting { verify(value).between(min, max) }

        val violation = assertIs<BetweenViolation<*>>(result.violations.first())
        assertEquals(min, violation.min)
        assertEquals(max, violation.max)
        assertEquals(value, violation.actual)
    }

    @Test
    fun greaterThan_valid() {
        val value = 6
        val boundary = 5

        assertTrue(validateCollecting { verify(value).greaterThan(boundary) }.isValid)
    }

    @Test
    fun greaterThan_invalid_equal() {
        val value = 5

        val result = validateCollecting { verify(value).greaterThan(value) }

        assertTrue(result.isInvalid)
        assertIs<GreaterThanViolation<*>>(result.violations.first())
    }

    @Test
    fun greaterThan_invalid_less() {
        val value = 4
        val boundary = 5

        assertTrue(validateCollecting { verify(value).greaterThan(boundary) }.isInvalid)
    }

    @Test
    fun greaterThan_violationValues() {
        val value = 4
        val boundary = 5

        val result = validateCollecting { verify(value).greaterThan(boundary) }

        val violation = assertIs<GreaterThanViolation<*>>(result.violations.first())
        assertEquals(boundary, violation.minExclusive)
        assertEquals(value, violation.actual)
    }

    @Test
    fun lessThan_valid() {
        val value = 4
        val boundary = 5

        assertTrue(validateCollecting { verify(value).lessThan(boundary) }.isValid)
    }

    @Test
    fun lessThan_invalid_equal() {
        val value = 5

        val result = validateCollecting { verify(value).lessThan(value) }

        assertTrue(result.isInvalid)
        assertIs<LessThanViolation<*>>(result.violations.first())
    }

    @Test
    fun lessThan_invalid_greater() {
        val value = 6
        val boundary = 5

        assertTrue(validateCollecting { verify(value).lessThan(boundary) }.isInvalid)
    }

    @Test
    fun lessThan_violationValues() {
        val value = 6
        val boundary = 5

        val result = validateCollecting { verify(value).lessThan(boundary) }

        val violation = assertIs<LessThanViolation<*>>(result.violations.first())
        assertEquals(boundary, violation.maxExclusive)
        assertEquals(value, violation.actual)
    }

    @Test
    fun rules_chain() {
        val value = 3
        val min = 1
        val max = 5

        assertTrue(validateCollecting { verify(value).atLeast(min).atMost(max) }.isValid)
    }
}
