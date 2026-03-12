package io.github.kverify.rules

import io.github.kverify.core.scope.validateCollecting
import io.github.kverify.core.scope.verify
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
    @Test
    fun notBlank_valid() {
        val value = "hello"

        assertTrue(validateCollecting { verify(value).notBlank() }.isValid)
    }

    @Test
    fun notBlank_invalid_blank() {
        val value = "   "

        val result = validateCollecting { verify(value).notBlank() }

        assertTrue(result.isInvalid)
        assertIs<NotBlankViolation>(result.violations.first())
    }

    @Test
    fun notBlank_invalid_empty() {
        assertTrue(validateCollecting { verify("").notBlank() }.isInvalid)
    }

    @Test
    fun notBlank_customReason() {
        val reason = "must provide a name"

        val result = validateCollecting { verify("").notBlank(reason) }

        val violation = assertIs<NotBlankViolation>(result.violations.first())
        assertEquals(reason, violation.reason)
    }

    @Test
    fun minLength_valid() {
        val value = "hello"
        val min = 3

        assertTrue(validateCollecting { verify(value).minLength(min) }.isValid)
    }

    @Test
    fun minLength_atBoundary() {
        val value = "abc"

        assertTrue(validateCollecting { verify(value).minLength(value.length) }.isValid)
    }

    @Test
    fun minLength_invalid() {
        val value = "hi"
        val min = 5

        val result = validateCollecting { verify(value).minLength(min) }

        assertTrue(result.isInvalid)
        assertIs<MinLengthViolation>(result.violations.first())
    }

    @Test
    fun minLength_violationValues() {
        val value = "ab"
        val min = 5

        val result = validateCollecting { verify(value).minLength(min) }

        val violation = assertIs<MinLengthViolation>(result.violations.first())
        assertEquals(min, violation.minLengthAllowed)
        assertEquals(value.length, violation.actualLength)
    }

    @Test
    fun maxLength_valid() {
        val value = "hi"
        val max = 5

        assertTrue(validateCollecting { verify(value).maxLength(max) }.isValid)
    }

    @Test
    fun maxLength_atBoundary() {
        val value = "abc"

        assertTrue(validateCollecting { verify(value).maxLength(value.length) }.isValid)
    }

    @Test
    fun maxLength_invalid() {
        val value = "toolong"
        val max = 3

        val result = validateCollecting { verify(value).maxLength(max) }

        assertTrue(result.isInvalid)
        assertIs<MaxLengthViolation>(result.violations.first())
    }

    @Test
    fun maxLength_violationValues() {
        val value = "toolong"
        val max = 3

        val result = validateCollecting { verify(value).maxLength(max) }

        val violation = assertIs<MaxLengthViolation>(result.violations.first())
        assertEquals(max, violation.maxLengthAllowed)
        assertEquals(value.length, violation.actualLength)
    }

    @Test
    fun exactLength_valid() {
        val value = "abc"

        assertTrue(validateCollecting { verify(value).exactLength(value.length) }.isValid)
    }

    @Test
    fun exactLength_invalid_tooShort() {
        val value = "ab"
        val length = 3

        val result = validateCollecting { verify(value).exactLength(length) }

        assertTrue(result.isInvalid)
        assertIs<ExactLengthViolation>(result.violations.first())
    }

    @Test
    fun exactLength_invalid_tooLong() {
        val value = "abcd"
        val length = 3

        assertTrue(validateCollecting { verify(value).exactLength(length) }.isInvalid)
    }

    @Test
    fun exactLength_violationValues() {
        val value = "ab"
        val length = 5

        val result = validateCollecting { verify(value).exactLength(length) }

        val violation = assertIs<ExactLengthViolation>(result.violations.first())
        assertEquals(length, violation.expectedLength)
        assertEquals(value.length, violation.actualLength)
    }

    @Test
    fun lengthRange_valid() {
        val value = "abc"
        val min = 2
        val max = 5

        assertTrue(validateCollecting { verify(value).lengthRange(min, max) }.isValid)
    }

    @Test
    fun lengthRange_atLowerBoundary() {
        val value = "ab"
        val max = 5

        assertTrue(validateCollecting { verify(value).lengthRange(value.length, max) }.isValid)
    }

    @Test
    fun lengthRange_atUpperBoundary() {
        val value = "abcde"
        val min = 2

        assertTrue(validateCollecting { verify(value).lengthRange(min, value.length) }.isValid)
    }

    @Test
    fun lengthRange_invalid_tooShort() {
        val value = "a"
        val min = 2
        val max = 5

        val result = validateCollecting { verify(value).lengthRange(min, max) }

        assertTrue(result.isInvalid)
        assertIs<LengthRangeViolation>(result.violations.first())
    }

    @Test
    fun lengthRange_invalid_tooLong() {
        val value = "abcdef"
        val min = 2
        val max = 5

        assertTrue(validateCollecting { verify(value).lengthRange(min, max) }.isInvalid)
    }

    @Test
    fun lengthRange_violationValues() {
        val value = "a"
        val min = 2
        val max = 5

        val result = validateCollecting { verify(value).lengthRange(min, max) }

        val violation = assertIs<LengthRangeViolation>(result.violations.first())
        assertEquals(min, violation.minLengthAllowed)
        assertEquals(max, violation.maxLengthAllowed)
        assertEquals(value.length, violation.actualLength)
    }

    @Test
    fun rules_chain() {
        val value = "hello"

        assertTrue(validateCollecting { verify(value).notBlank().minLength(3).maxLength(10) }.isValid)
    }

    @Test
    fun rules_collectMultipleViolations() {
        val value = ""
        val min = 3

        val result = validateCollecting { verify(value).notBlank().minLength(min) }

        val expectedViolationCount = 2
        assertEquals(expectedViolationCount, result.violations.size)
    }
}
