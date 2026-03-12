package io.github.kverify.core.result

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidationResultTest {
    private val sampleViolation = violation("validation failed")
    private val validResult = ValidationResult(emptyList())
    private val invalidResult = ValidationResult(listOf(sampleViolation))

    @Test
    fun isValid_trueWhenNoViolations() {
        assertTrue(validResult.isValid)
        assertFalse(validResult.isInvalid)
    }

    @Test
    fun isInvalid_trueWhenHasViolations() {
        assertFalse(invalidResult.isValid)
        assertTrue(invalidResult.isInvalid)
    }

    @Test
    fun violations_accessible() {
        val violations = listOf(sampleViolation)

        val result = ValidationResult(violations)

        assertEquals(violations, result.violations)
    }

    @Test
    fun onValid_calledWhenValid() {
        var called = false

        validResult.onValid { called = true }

        assertTrue(called)
    }

    @Test
    fun onValid_notCalledWhenInvalid() {
        var called = false

        invalidResult.onValid { called = true }

        assertFalse(called)
    }

    @Test
    fun onInvalid_calledWithViolations() {
        var received: List<Violation>? = null

        invalidResult.onInvalid { received = it }

        assertEquals(sampleViolation, received!!.single())
    }

    @Test
    fun onInvalid_notCalledWhenValid() {
        var called = false

        validResult.onInvalid { called = true }

        assertFalse(called)
    }

    @Test
    fun fold_returnsOnValidBranch() {
        val validBranch = "valid"
        val invalidBranch = "invalid"

        val result = validResult.fold(onValid = { validBranch }, onInvalid = { invalidBranch })

        assertEquals(validBranch, result)
    }

    @Test
    fun fold_returnsOnInvalidBranch() {
        val validBranch = "valid"
        val invalidBranch = "invalid"

        val result = invalidResult.fold(onValid = { validBranch }, onInvalid = { invalidBranch })

        assertEquals(invalidBranch, result)
    }

    @Test
    fun throwOnInvalid_throwsWithViolations() {
        val ex = assertFailsWith<ValidationException> {
            invalidResult.throwOnInvalid()
        }

        assertEquals(sampleViolation, ex.violations.single())
    }

    @Test
    fun throwOnInvalid_doesNotThrowWhenValid() {
        validResult.throwOnInvalid()
    }
}
