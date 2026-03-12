package io.github.kverify.rules

import io.github.kverify.core.scope.validateCollecting
import io.github.kverify.core.scope.verify
import io.github.kverify.violations.EqualToViolation
import io.github.kverify.violations.NoneOfViolation
import io.github.kverify.violations.NotEqualToViolation
import io.github.kverify.violations.NotNullViolation
import io.github.kverify.violations.OneOfViolation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class EqualityRulesTest {
    @Test
    fun notNull_valid() {
        val value = "present"

        assertTrue(validateCollecting { verify(value).notNull() }.isValid)
    }

    @Test
    fun notNull_invalid() {
        val value: String? = null

        val result = validateCollecting { verify(value).notNull() }

        assertTrue(result.isInvalid)
        assertIs<NotNullViolation>(result.violations.first())
    }

    @Test
    fun notNull_nonNullableType_valid() {
        assertTrue(validateCollecting { verify(42).notNull() }.isValid)
    }

    @Test
    fun notNull_customReason() {
        val reason = "ID is required"

        val result = validateCollecting { verify<String?>(null).notNull(reason) }

        val violation = assertIs<NotNullViolation>(result.violations.first())
        assertEquals(reason, violation.reason)
    }

    @Test
    fun equalTo_valid() {
        val value = "hello"

        assertTrue(validateCollecting { verify(value).equalTo(value) }.isValid)
    }

    @Test
    fun equalTo_invalid() {
        val value = "hello"
        val expected = "world"

        val result = validateCollecting { verify(value).equalTo(expected) }

        assertTrue(result.isInvalid)
        assertIs<EqualToViolation<*>>(result.violations.first())
    }

    @Test
    fun equalTo_violationValues() {
        val value = "hello"
        val expected = "world"

        val result = validateCollecting { verify(value).equalTo(expected) }

        val violation = assertIs<EqualToViolation<*>>(result.violations.first())
        assertEquals(expected, violation.expected)
        assertEquals(value, violation.actual)
    }

    @Test
    fun notEqualTo_valid() {
        val value = "hello"
        val forbidden = "world"

        assertTrue(validateCollecting { verify(value).notEqualTo(forbidden) }.isValid)
    }

    @Test
    fun notEqualTo_invalid() {
        val value = "same"

        val result = validateCollecting { verify(value).notEqualTo(value) }

        assertTrue(result.isInvalid)
        assertIs<NotEqualToViolation<*>>(result.violations.first())
    }

    @Test
    fun notEqualTo_violationValues() {
        val value = "same"

        val result = validateCollecting { verify(value).notEqualTo(value) }

        val violation = assertIs<NotEqualToViolation<*>>(result.violations.first())
        assertEquals(value, violation.forbidden)
    }

    @Test
    fun oneOf_valid() {
        val value = "a"
        val allowed = setOf("a", "b", "c")

        assertTrue(validateCollecting { verify(value).oneOf(allowed) }.isValid)
    }

    @Test
    fun oneOf_invalid() {
        val value = "d"
        val allowed = setOf("a", "b", "c")

        val result = validateCollecting { verify(value).oneOf(allowed) }

        assertTrue(result.isInvalid)
        assertIs<OneOfViolation<*>>(result.violations.first())
    }

    @Test
    fun oneOf_violationValues() {
        val value = "d"
        val allowed = setOf("a", "b")

        val result = validateCollecting { verify(value).oneOf(allowed) }

        val violation = assertIs<OneOfViolation<*>>(result.violations.first())
        assertEquals(allowed, violation.allowed)
        assertEquals(value, violation.actual)
    }

    @Test
    fun noneOf_valid() {
        val value = "d"
        val forbidden = setOf("a", "b", "c")

        assertTrue(validateCollecting { verify(value).noneOf(forbidden) }.isValid)
    }

    @Test
    fun noneOf_invalid() {
        val value = "a"
        val forbidden = setOf("a", "b", "c")

        val result = validateCollecting { verify(value).noneOf(forbidden) }

        assertTrue(result.isInvalid)
        assertIs<NoneOfViolation<*>>(result.violations.first())
    }

    @Test
    fun noneOf_violationValues() {
        val value = "a"
        val forbidden = setOf("a", "b")

        val result = validateCollecting { verify(value).noneOf(forbidden) }

        val violation = assertIs<NoneOfViolation<*>>(result.violations.first())
        assertEquals(forbidden, violation.forbidden)
        assertEquals(value, violation.actual)
    }
}
