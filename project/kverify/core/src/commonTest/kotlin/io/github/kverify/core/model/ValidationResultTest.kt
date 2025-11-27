package io.github.kverify.core.model

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail

class ValidationResultTest {
    @Test
    fun validHasCorrectProperties() {
        val result: ValidationResult = ValidationResult.Valid

        assertTrue(result.isValid)
        assertFalse(result.isInvalid)
    }

    @Test
    fun validToStringReturnsCorrectString() {
        val result = ValidationResult.Valid

        assertEquals("ValidationResult.Valid", result.toString())
    }

    @Test
    fun invalidWithListHasCorrectProperties() {
        val violations = listOf(violation("error1"), violation("error2"))
        val result = ValidationResult.Invalid(violations)

        assertFalse(result.isValid)
        assertTrue(result.isInvalid)
        assertEquals(violations, result.violations)
    }

    @Test
    fun invalidWithSingleViolationConstructor() {
        val violation = violation("error")
        val result = ValidationResult.Invalid(violation)

        assertFalse(result.isValid)
        assertTrue(result.isInvalid)
        assertEquals(listOf(violation), result.violations)
    }

    @Test
    fun invalidWithVarargConstructor() {
        val violation1 = violation("error1")
        val violation2 = violation("error2")
        val result = ValidationResult.Invalid(violation1, violation2)

        assertFalse(result.isValid)
        assertTrue(result.isInvalid)
        assertEquals(listOf(violation1, violation2), result.violations)
    }

    @Test
    fun invalidToStringReturnsCorrectString() {
        val violations = listOf(violation("error"))
        val result = ValidationResult.Invalid(violations).toString()

        assertEquals(result, "ValidationResult.Invalid(violations=$violations)")
    }

    @Test
    fun violationsOrEmptyReturnsEmptyListForValid() {
        val result: ValidationResult = ValidationResult.Valid

        val violations = result.violationsOrEmpty()

        assertTrue(violations.isEmpty())
    }

    @Test
    fun violationsOrEmptyReturnsViolationsForInvalid() {
        val violations = listOf(violation("error1"), violation("error2"))
        val result = ValidationResult.Invalid(violations)

        val resultViolations = result.violationsOrEmpty()

        assertEquals(violations, resultViolations)
    }

    @Test
    fun violationsOrNullReturnsNullForValid() {
        val result: ValidationResult = ValidationResult.Valid

        val violations = result.violationsOrNull()

        assertNull(violations)
    }

    @Test
    fun violationsOrNullReturnsViolationsForInvalid() {
        val violations = listOf(violation("error"))
        val result = ValidationResult.Invalid(violations)

        val resultViolations = result.violationsOrNull()

        assertNotNull(resultViolations)
        assertEquals(violations, resultViolations)
    }

    @Test
    fun validationResultFactoryWithNoArgumentsReturnsValid() {
        val result = ValidationResult()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun validationResultFactoryWithSingleViolationReturnsInvalid() {
        val violation = violation("error")
        val result = ValidationResult(violation)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation), result.violations)
    }

    @Test
    fun validationResultFactoryWithVarargReturnsInvalid() {
        val violation1 = violation("error1")
        val violation2 = violation("error2")
        val result = ValidationResult(violation1, violation2)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation1, violation2), result.violations)
    }

    @Test
    fun validationResultFactoryWithEmptyListReturnsValid() {
        val result = ValidationResult(emptyList())

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun validationResultFactoryWithNonEmptyListReturnsInvalid() {
        val violations = listOf(violation("error1"), violation("error2"))
        val result = ValidationResult(violations)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violations, result.violations)
    }

    @Test
    fun plusOperatorCombinesTwoInvalidResults() {
        val violations1 = listOf(violation("error1"))
        val violations2 = listOf(violation("error2"))
        val result1 = ValidationResult.Invalid(violations1)
        val result2 = ValidationResult.Invalid(violations2)

        val combined = result1 + result2

        assertIs<ValidationResult.Invalid>(combined)
        assertEquals(violations1 + violations2, combined.violations)
    }

    @Test
    fun plusOperatorInvalidAndValidReturnsInvalid() {
        val violations = listOf(violation("error"))
        val invalid = ValidationResult.Invalid(violations)
        val valid = ValidationResult.Valid

        val combined = invalid + valid

        assertIs<ValidationResult.Invalid>(combined)
        assertEquals(violations, combined.violations)
    }

    @Test
    fun plusOperatorValidAndInvalidReturnsInvalid() {
        val violations = listOf(violation("error"))
        val valid = ValidationResult.Valid
        val invalid = ValidationResult.Invalid(violations)

        val combined = valid + invalid

        assertIs<ValidationResult.Invalid>(combined)
        assertEquals(violations, combined.violations)
    }

    @Test
    fun plusOperatorTwoValidReturnsValid() {
        val valid1 = ValidationResult.Valid
        val valid2 = ValidationResult.Valid

        val combined = valid1 + valid2

        assertIs<ValidationResult.Valid>(combined)
    }

    @Test
    fun plusOperatorWithSingleViolationOnValid() {
        val valid = ValidationResult.Valid
        val violation = violation("error")

        val result = valid + violation

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation), result.violations)
    }

    @Test
    fun plusOperatorWithSingleViolationOnInvalid() {
        val existingViolations = listOf(violation("error1"))
        val invalid = ValidationResult.Invalid(existingViolations)
        val newViolation = violation("error2")

        val result = invalid + newViolation

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(existingViolations + newViolation, result.violations)
    }

    @Test
    fun plusOperatorWithViolationListOnValid() {
        val valid = ValidationResult.Valid
        val violations = listOf(violation("error1"), violation("error2"))

        val result = valid + violations

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violations, result.violations)
    }

    @Test
    fun plusOperatorWithViolationListOnInvalid() {
        val existingViolations = listOf(violation("error1"))
        val invalid = ValidationResult.Invalid(existingViolations)
        val newViolations = listOf(violation("error2"), violation("error3"))

        val result = invalid + newViolations

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(existingViolations + newViolations, result.violations)
    }

    @Test
    fun plusOperatorWithEmptyViolationListReturnsUnchanged() {
        val valid = ValidationResult.Valid
        val result = valid + emptyList<Violation>()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun plusOperatorWithValidationResultListOnValid() {
        val valid = ValidationResult.Valid
        val results =
            listOf(
                ValidationResult.Invalid(violation("error1")),
                ValidationResult.Invalid(violation("error2")),
            )

        val result = valid + results

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(2, result.violations.size)
    }

    @Test
    fun plusOperatorWithValidationResultListOnInvalid() {
        val invalid = ValidationResult.Invalid(violation("error1"))
        val results =
            listOf(
                ValidationResult.Invalid(violation("error2")),
                ValidationResult.Valid,
            )

        val result = invalid + results

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(2, result.violations.size)
    }

    @Test
    fun plusOperatorWithEmptyValidationResultListReturnsUnchanged() {
        val valid = ValidationResult.Valid
        val result = valid + emptyList<ValidationResult>()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun plusOperatorWithValidationResultListIgnoresValid() {
        val results =
            listOf(
                ValidationResult.Valid,
                ValidationResult.Invalid(violation("error")),
                ValidationResult.Valid,
            )

        val result = ValidationResult.Valid + results

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(1, result.violations.size)
    }

    @Test
    fun mergeValidationResultsWithEmptyListReturnsValid() {
        val result = emptyList<ValidationResult>().mergeValidationResults()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun mergeValidationResultsWithOnlyValidReturnsValid() {
        val results = listOf(ValidationResult.Valid, ValidationResult.Valid)

        val result = results.mergeValidationResults()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun mergeValidationResultsWithInvalidResultsMergesViolations() {
        val results =
            listOf(
                ValidationResult.Invalid(violation("error1")),
                ValidationResult.Valid,
                ValidationResult.Invalid(violation("error2"), violation("error3")),
            )

        val result = results.mergeValidationResults()

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(3, result.violations.size)
        assertEquals("error1", result.violations[0].reason)
        assertEquals("error2", result.violations[1].reason)
        assertEquals("error3", result.violations[2].reason)
    }

    @Test
    fun ifValidExecutesBlockForValid() {
        var executed = false
        val result: ValidationResult = ValidationResult.Valid

        val value =
            result.ifValid {
                executed = true
                "success"
            }

        assertTrue(executed)
        assertEquals("success", value)
    }

    @Test
    fun ifValidDoesNotExecuteBlockForInvalid() {
        var executed = false
        val result = ValidationResult.Invalid(violation("error"))

        val value =
            result.ifValid {
                executed = true
                "success"
            }

        assertFalse(executed)
        assertNull(value)
    }

    @Test
    fun ifInvalidExecutesBlockForInvalid() {
        var executedViolations: List<Violation>? = null
        val violations = listOf(violation("error"))
        val result = ValidationResult.Invalid(violations)

        val value =
            result.ifInvalid { v ->
                executedViolations = v
                "handled"
            }

        assertEquals(violations, executedViolations)
        assertEquals("handled", value)
    }

    @Test
    fun ifInvalidDoesNotExecuteBlockForValid() {
        var executed = false
        val result: ValidationResult = ValidationResult.Valid

        val value =
            result.ifInvalid {
                executed = true
                "handled"
            }

        assertFalse(executed)
        assertNull(value)
    }

    @Test
    fun foldExecutesIfValidForValid() {
        val result: ValidationResult = ValidationResult.Valid

        val value =
            result.fold(
                ifValid = { "valid" },
                ifInvalid = { "invalid" },
            )

        assertEquals("valid", value)
    }

    @Test
    fun foldExecutesIfInvalidForInvalid() {
        val violations = listOf(violation("error"))
        val result = ValidationResult.Invalid(violations)

        val value =
            result.fold(
                ifValid = { "valid" },
                ifInvalid = { v -> "invalid: ${v.size}" },
            )

        assertEquals("invalid: 1", value)
    }

    @Test
    fun onValidExecutesBlockForValid() {
        var executed = false
        val result: ValidationResult = ValidationResult.Valid

        val returned =
            result.onValid {
                executed = true
            }

        assertTrue(executed)
        assertEquals(result, returned)
    }

    @Test
    fun onValidDoesNotExecuteBlockForInvalid() {
        var executed = false
        val result = ValidationResult.Invalid(violation("error"))

        val returned =
            result.onValid {
                executed = true
            }

        assertFalse(executed)
        assertEquals(result, returned)
    }

    @Test
    fun onValidReturnsOriginalResult() {
        val result: ValidationResult = ValidationResult.Valid

        val returned = result.onValid { }

        assertEquals(result, returned)
    }

    @Test
    fun onInvalidExecutesBlockForInvalid() {
        var executedViolations: List<Violation>? = null
        val violations = listOf(violation("error"))
        val result = ValidationResult.Invalid(violations)

        val returned =
            result.onInvalid { v ->
                executedViolations = v
            }

        assertEquals(violations, executedViolations)
        assertEquals(result, returned)
    }

    @Test
    fun onInvalidDoesNotExecuteBlockForValid() {
        var executed = false
        val result: ValidationResult = ValidationResult.Valid

        val returned =
            result.onInvalid {
                executed = true
            }

        assertFalse(executed)
        assertEquals(result, returned)
    }

    @Test
    fun onInvalidReturnsOriginalResult() {
        val result = ValidationResult.Invalid(violation("error"))

        val returned = result.onInvalid { }

        assertEquals(result, returned)
    }

    @Test
    fun onValidAndOnInvalidCanBeChained() {
        var validExecuted = false
        var invalidExecuted = false

        ValidationResult.Valid
            .onValid { validExecuted = true }
            .onInvalid { invalidExecuted = true }

        assertTrue(validExecuted)
        assertFalse(invalidExecuted)
    }

    @Test
    fun throwOnFailureDoesNotThrowForValid() {
        val result: ValidationResult = ValidationResult.Valid

        // Should not throw
        result.throwOnFailure()
    }

    @Test
    fun throwOnFailureThrowsForInvalid() {
        val violation = violation("error message")
        val result = ValidationResult.Invalid(violation)

        try {
            result.throwOnFailure()
            fail("Expected ValidationException")
        } catch (e: ValidationException) {
            assertEquals(listOf(violation), e.violations)
            assertTrue(e.message?.contains("Validation failed") == true)
            assertTrue(e.message?.contains("error message") == true)
        }
    }

    @Test
    fun throwOnFailureWithCustomSeparator() {
        val violations = listOf(violation("error1"), violation("error2"))
        val result = ValidationResult.Invalid(violations)

        try {
            result.throwOnFailure(separator = " | ")
            fail("Expected ValidationException")
        } catch (e: ValidationException) {
            assertTrue(e.message?.contains("|") == true)
        }
    }

    @Test
    fun throwOnFailureWithCustomPrefix() {
        val violation = violation("error")
        val result = ValidationResult.Invalid(violation)

        try {
            result.throwOnFailure(prefix = "Custom prefix: ")
            fail("Expected ValidationException")
        } catch (e: ValidationException) {
            assertTrue(e.message?.startsWith("Custom prefix:") == true)
        }
    }

    @Test
    fun throwOnFailureWithCustomPostfix() {
        val violation = violation("error")
        val result = ValidationResult.Invalid(violation)

        try {
            result.throwOnFailure(postfix = " [END]")
            fail("Expected ValidationException")
        } catch (e: ValidationException) {
            assertTrue(e.message?.endsWith("[END]") == true)
        }
    }

    @Test
    fun throwOnFailureWithLimit() {
        val violations =
            listOf(
                violation("error1"),
                violation("error2"),
                violation("error3"),
            )
        val result = ValidationResult.Invalid(violations)

        try {
            result.throwOnFailure(limit = 2, truncated = "...")
            fail("Expected ValidationException")
        } catch (e: ValidationException) {
            assertTrue(e.message?.contains("...") == true)
        }
    }

    @Test
    fun throwOnFailureWithCustomTransform() {
        val violation = violation("error")
        val result = ValidationResult.Invalid(violation)

        try {
            result.throwOnFailure(transform = { ">> ${it.reason}" })
            fail("Expected ValidationException")
        } catch (e: ValidationException) {
            assertTrue(e.message?.contains(">>") == true)
        }
    }

    @Test
    fun throwOnFailureWithCause() {
        val violation = violation("error")
        val result = ValidationResult.Invalid(violation)
        val cause = RuntimeException("underlying cause")

        try {
            result.throwOnFailure(cause = cause)
            fail("Expected ValidationException")
        } catch (e: ValidationException) {
            assertEquals(cause, e.cause)
        }
    }

    @Test
    fun throwOnFailureWithEmptyViolationsUsesOnEmptyPrefix() {
        val result = ValidationResult.Invalid(emptyList())

        try {
            result.throwOnFailure(onEmptyPrefix = "No violations but failed")
            fail("Expected ValidationException")
        } catch (e: ValidationException) {
            assertEquals("No violations but failed", e.message)
        }
    }

    @Test
    fun throwOnFailurePreservesViolationsInException() {
        val violations = listOf(violation("error1"), violation("error2"))
        val result = ValidationResult.Invalid(violations)

        try {
            result.throwOnFailure()
            fail("Expected ValidationException")
        } catch (e: ValidationException) {
            assertEquals(violations, e.violations)
        }
    }

    @Test
    fun chainedPlusOperations() {
        val result =
            ValidationResult.Valid +
                violation("error1") +
                violation("error2") +
                listOf(violation("error3"))

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(3, result.violations.size)
    }

    @Test
    fun invalidWithEmptyViolationsList() {
        val result = ValidationResult.Invalid(emptyList())

        assertIs<ValidationResult.Invalid>(result)
        assertTrue(result.violations.isEmpty())
        assertFalse(result.isValid)
        assertTrue(result.isInvalid)
    }

    @Test
    fun validationResultIsSealed() {
        val result: ValidationResult = ValidationResult.Valid

        val isValidOrInvalid =
            when (result) {
                is ValidationResult.Valid -> true
                is ValidationResult.Invalid -> true
            }

        assertTrue(isValidOrInvalid)
    }

    @Test
    fun complexMergeScenario() {
        val result1 = ValidationResult.Invalid(violation("error1"))
        val result2 = ValidationResult.Valid
        val result3 = ValidationResult.Invalid(violation("error2"), violation("error3"))

        val merged = listOf(result1, result2, result3).mergeValidationResults()

        assertIs<ValidationResult.Invalid>(merged)
        assertEquals(3, merged.violations.size)
    }
}
