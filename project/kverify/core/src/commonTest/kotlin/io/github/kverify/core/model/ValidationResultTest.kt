package io.github.kverify.core.model

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.test.fail

class ValidationResultTest {
    @Test
    fun isValidReturnsTrueWhenViolationsIsEmpty() {
        val result = ValidationResult(emptyList())

        assertTrue(result.isValid)
    }

    @Test
    fun isInvalidReturnsFalseWhenViolationsIsEmpty() {
        val result = ValidationResult(emptyList())

        assertFalse(result.isInvalid)
    }

    @Test
    fun isValidReturnsFalseWhenViolationsIsNotEmpty() {
        val result = ValidationResult(listOf(violation("reason")))

        assertFalse(result.isValid)
    }

    @Test
    fun isInvalidReturnsTrueWhenViolationsIsNotEmpty() {
        val result = ValidationResult(listOf(violation("reason")))

        assertTrue(result.isInvalid)
    }

    @Test
    fun isValidAndIsInvalidAreMutuallyExclusive() {
        val valid = ValidationResult(emptyList())
        val invalid = ValidationResult(listOf(violation("reason")))

        assertFalse(valid.isValid && valid.isInvalid)
        assertFalse(invalid.isValid && invalid.isInvalid)
    }

    @Test
    fun violationsAreStoredAndRetrievable() {
        val violations = listOf(violation("first"), violation("second"))

        val result = ValidationResult(violations)

        assertEquals(violations, result.violations)
    }

    @Test
    fun onValidInvokesBlockWhenValid() {
        val result = ValidationResult(emptyList())
        var invoked = false

        result.onValid { invoked = true }

        assertTrue(invoked)
    }

    @Test
    fun onValidDoesNotInvokeBlockWhenInvalid() {
        val result = ValidationResult(listOf(violation("reason")))
        var invoked = false

        result.onValid { invoked = true }

        assertFalse(invoked)
    }

    @Test
    fun onInvalidInvokesBlockWhenInvalid() {
        val result = ValidationResult(listOf(violation("reason")))
        var invoked = false

        result.onInvalid { invoked = true }

        assertTrue(invoked)
    }

    @Test
    fun onInvalidPassesViolationsToBlock() {
        val violations = listOf(violation("a"), violation("b"))
        val result = ValidationResult(violations)
        var received: List<Violation>? = null

        result.onInvalid { received = it }

        assertEquals(violations, received)
    }

    @Test
    fun onInvalidDoesNotInvokeBlockWhenValid() {
        val result = ValidationResult(emptyList())
        var invoked = false

        result.onInvalid { invoked = true }

        assertFalse(invoked)
    }

    @Test
    fun foldCallsOnValidWhenValid() {
        val result = ValidationResult(emptyList())
        val onValid = "valid"

        val value = result.fold(onValid = { onValid }, onInvalid = { "invalid" })

        assertEquals(onValid, value)
    }

    @Test
    fun foldCallsOnInvalidWhenInvalid() {
        val result = ValidationResult(listOf(violation("reason")))
        val onInvalid = "invalid"

        val value = result.fold(onValid = { "valid" }, onInvalid = { onInvalid })

        assertEquals(onInvalid, value)
    }

    @Test
    fun foldPassesViolationsToOnInvalid() {
        val violations = listOf(violation("x"), violation("y"))
        val result = ValidationResult(violations)

        val received = result.fold(onValid = { emptyList() }, onInvalid = { it })

        assertEquals(violations, received)
    }

    @Test
    fun foldInvokesExactlyOneLambda() {
        val validResult = ValidationResult(emptyList())
        val invalidResult = ValidationResult(listOf(violation("reason")))
        var validOnValidCount = 0
        var validOnInvalidCount = 0
        var invalidOnValidCount = 0
        var invalidOnInvalidCount = 0

        validResult.fold(onValid = { validOnValidCount++ }, onInvalid = { validOnInvalidCount++ })
        invalidResult.fold(onValid = { invalidOnValidCount++ }, onInvalid = { invalidOnInvalidCount++ })

        assertEquals(1, validOnValidCount)
        assertEquals(0, validOnInvalidCount)
        assertEquals(0, invalidOnValidCount)
        assertEquals(1, invalidOnInvalidCount)
    }

    @Test
    fun throwOnInvalidDoesNotThrowWhenValid() {
        val result = ValidationResult(emptyList())

        result.throwOnInvalid()
    }

    @Test
    fun throwOnInvalidThrowsValidationExceptionWhenInvalid() {
        val result = ValidationResult(listOf(violation("must not be empty")))

        val exception =
            try {
                result.throwOnInvalid()
                fail("Expected ValidationException to be thrown")
            } catch (e: ValidationException) {
                e
            }

        assertIs<ValidationException>(exception)
    }

    @Test
    fun throwOnInvalidExceptionContainsAllViolations() {
        val violations = listOf(violation("first"), violation("second"), violation("third"))
        val result = ValidationResult(violations)

        val exception =
            try {
                result.throwOnInvalid()
                fail("Expected ValidationException to be thrown")
            } catch (e: ValidationException) {
                e
            }

        assertEquals(violations, exception.violations)
    }
}
