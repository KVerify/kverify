package io.github.kverify.core.model

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.util.violations
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ValidationResultTest {
    private val violation = violation("error")
    private val violationList = violations("error1", "error2")

    @Test
    fun validResultIsValid() {
        val result = ValidationResult.Valid

        assertTrue(result.isValid)
        assertFalse(result.isInvalid)
    }

    @Test
    fun invalidResultIsInvalid() {
        val result = ValidationResult.Invalid(violations("error"))

        assertFalse(result.isValid)
        assertTrue(result.isInvalid)
    }

    @Test
    fun invalidResultWithSingleViolation() {
        val result = ValidationResult.Invalid(violation)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun invalidResultWithMultipleViolations() {
        val result = ValidationResult.Invalid(violationList)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
    }

    @Test
    fun invalidResultWithVarargConstructor() {
        val result = ValidationResult.Invalid(*violationList.toTypedArray())

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
    }

    @Test
    fun factoryFunctionReturnsValid() {
        val result = ValidationResult()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun factoryFunctionWithSingleViolation() {
        val result = ValidationResult(violation)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun factoryFunctionWithVarargViolations() {
        val result = ValidationResult(*violationList.toTypedArray())

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
    }

    @Test
    fun factoryFunctionWithEmptyListReturnsValid() {
        val result = ValidationResult(emptyList())

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun factoryFunctionWithNonEmptyListReturnsInvalid() {
        val result = ValidationResult(violationList)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
    }

    @Test
    fun violationsOrEmptyReturnsEmptyListForValid() {
        val result = ValidationResult.Valid

        assertEquals(emptyList(), result.violationsOrEmpty())
    }

    @Test
    fun violationsOrEmptyReturnsViolationsForInvalid() {
        val result = ValidationResult.Invalid(violationList)

        assertEquals(violationList, result.violationsOrEmpty())
    }

    @Test
    fun violationsOrNullReturnsNullForValid() {
        val result = ValidationResult.Valid

        assertNull(result.violationsOrNull())
    }

    @Test
    fun violationsOrNullReturnsViolationsForInvalid() {
        val result = ValidationResult.Invalid(violationList)

        assertNotNull(result.violationsOrNull())
        assertEquals(violationList, result.violationsOrNull()!!)
    }

    @Test
    fun plusOperatorWithTwoInvalidResults() {
        val violations1 = violations("error1", "error2")
        val violations2 = violations("error3", "error4")
        val result1 = ValidationResult.Invalid(violations1)
        val result2 = ValidationResult.Invalid(violations2)

        val combined = result1 + result2

        assertIs<ValidationResult.Invalid>(combined)
        assertEquals(violations1 + violations2, combined.violations)
    }

    @Test
    fun plusOperatorWithInvalidAndValid() {
        val invalid = ValidationResult.Invalid(violationList)
        val valid = ValidationResult.Valid

        val combined = invalid + valid

        assertIs<ValidationResult.Invalid>(combined)
        assertEquals(violationList, combined.violations)
    }

    @Test
    fun plusOperatorWithValidAndInvalid() {
        val valid = ValidationResult.Valid
        val invalid = ValidationResult.Invalid(violationList)

        val combined = valid + invalid

        assertIs<ValidationResult.Invalid>(combined)
        assertEquals(violationList, combined.violations)
    }

    @Test
    fun plusOperatorWithTwoValidResults() {
        val result1 = ValidationResult.Valid
        val result2 = ValidationResult.Valid

        val combined = result1 + result2

        assertIs<ValidationResult.Valid>(combined)
    }

    @Test
    fun plusOperatorWithSingleViolation() {
        val result = ValidationResult.Invalid(violationList)

        val combined = result + violation

        assertIs<ValidationResult.Invalid>(combined)
        assertEquals(violationList + violation, combined.violations)
    }

    @Test
    fun plusOperatorWithSingleViolationOnValid() {
        val result = ValidationResult.Valid

        val combined = result + violation

        assertIs<ValidationResult.Invalid>(combined)
        assertEquals(violation, combined.violations.single())
    }

    @Test
    fun plusOperatorWithViolationList() {
        val violations1 = violations("error1", "error2")
        val violations2 = violations("error3", "error4")
        val result = ValidationResult.Invalid(violations1)

        val combined = result + violations2

        assertIs<ValidationResult.Invalid>(combined)
        assertEquals(violations1 + violations2, combined.violations)
    }

    @Test
    fun plusOperatorWithEmptyViolationList() {
        val result = ValidationResult.Invalid(violationList)

        val combined = result + emptyList<Violation>()

        assertIs<ValidationResult.Invalid>(combined)
        assertEquals(violationList, combined.violations)
    }

    @Test
    fun plusOperatorWithViolationListOnValid() {
        val result = ValidationResult.Valid

        val combined = result + violationList

        assertIs<ValidationResult.Invalid>(combined)
        assertEquals(violationList, combined.violations)
    }

    @Test
    fun plusOperatorWithValidationResultList() {
        val violations1 = violations("error1", "error2")
        val violations2 = violations("error3", "error4")
        val result = ValidationResult.Invalid(violations1)
        val results =
            listOf(
                ValidationResult.Invalid(violations2),
                ValidationResult.Valid,
            )

        val combined = result + results

        assertIs<ValidationResult.Invalid>(combined)
        assertEquals(violations1 + violations2, combined.violations)
    }

    @Test
    fun plusOperatorWithEmptyValidationResultList() {
        val result = ValidationResult.Invalid(violationList)

        val combined = result + emptyList<ValidationResult>()

        assertIs<ValidationResult.Invalid>(combined)
        assertEquals(violationList, combined.violations)
    }

    @Test
    fun mergeValidationResultsWithAllValid() {
        val results =
            listOf(
                ValidationResult.Valid,
                ValidationResult.Valid,
                ValidationResult.Valid,
            )

        val merged = results.mergeValidationResults()

        assertIs<ValidationResult.Valid>(merged)
    }

    @Test
    fun mergeValidationResultsWithMixedResults() {
        val violations1 = violations("error1", "error2")
        val violations2 = violations("error3")
        val results =
            listOf(
                ValidationResult.Valid,
                ValidationResult.Invalid(violations1),
                ValidationResult.Valid,
                ValidationResult.Invalid(violations2),
            )

        val merged = results.mergeValidationResults()

        assertIs<ValidationResult.Invalid>(merged)
        assertEquals(violations1 + violations2, merged.violations)
    }

    @Test
    fun mergeValidationResultsWithEmptyList() {
        val merged = emptyList<ValidationResult>().mergeValidationResults()

        assertIs<ValidationResult.Valid>(merged)
    }

    @Test
    fun ifValidExecutesBlockWhenValid() {
        val result = ValidationResult.Valid
        var executed = false

        val returned =
            result.ifValid {
                executed = true
                "success"
            }

        assertTrue(executed)
        assertEquals("success", returned)
    }

    @Test
    fun ifValidDoesNotExecuteBlockWhenInvalid() {
        val result = ValidationResult.Invalid(violations("error"))
        var executed = false

        val returned =
            result.ifValid {
                executed = true
                "success"
            }

        assertFalse(executed)
        assertNull(returned)
    }

    @Test
    fun ifInvalidExecutesBlockWhenInvalid() {
        val result = ValidationResult.Invalid(violationList)
        var capturedViolations: List<*>? = null

        val returned =
            result.ifInvalid {
                capturedViolations = it
                "failure"
            }

        assertEquals(violationList, capturedViolations!!)
        assertEquals("failure", returned)
    }

    @Test
    fun ifInvalidDoesNotExecuteBlockWhenValid() {
        val result = ValidationResult.Valid
        var executed = false

        val returned =
            result.ifInvalid {
                executed = true
                "failure"
            }

        assertFalse(executed)
        assertNull(returned)
    }

    @Test
    fun foldExecutesIfValidWhenValid() {
        val result = ValidationResult.Valid

        val returned =
            result.fold(
                ifValid = { "valid" },
                ifInvalid = { "invalid" },
            )

        assertEquals("valid", returned)
    }

    @Test
    fun foldExecutesIfInvalidWhenInvalid() {
        val result = ValidationResult.Invalid(violationList)

        val returned =
            result.fold(
                ifValid = { "valid" },
                ifInvalid = { violations -> violations.size.toString() },
            )

        assertEquals("2", returned)
    }

    @Test
    fun onValidExecutesBlockWhenValid() {
        val result = ValidationResult.Valid
        var executed = false

        val returned = result.onValid { executed = true }

        assertTrue(executed)
        assertEquals(result, returned)
    }

    @Test
    fun onValidDoesNotExecuteBlockWhenInvalid() {
        val result = ValidationResult.Invalid(violations("error"))
        var executed = false

        val returned = result.onValid { executed = true }

        assertFalse(executed)
        assertEquals(result, returned)
    }

    @Test
    fun onInvalidExecutesBlockWhenInvalid() {
        val result = ValidationResult.Invalid(violationList)
        var capturedViolations: List<*>? = null

        val returned = result.onInvalid { capturedViolations = it }

        assertEquals(violationList, capturedViolations!!)
        assertEquals(result, returned)
    }

    @Test
    fun onInvalidDoesNotExecuteBlockWhenValid() {
        val result = ValidationResult.Valid
        var executed = false

        val returned = result.onInvalid { executed = true }

        assertFalse(executed)
        assertEquals(result, returned)
    }

    @Test
    fun throwOnFailureDoesNotThrowWhenValid() {
        val result = ValidationResult.Valid

        result.throwOnFailure()
    }

    @Test
    fun throwOnFailureThrowsWhenInvalid() {
        val result = ValidationResult.Invalid(violationList)

        try {
            result.throwOnFailure()
            error("Expected ValidationException to be thrown")
        } catch (e: ValidationException) {
            assertEquals(violationList, e.violations)
        }
    }

    @Test
    fun throwOnFailureWithCustomParameters() {
        val result = ValidationResult.Invalid(violationList)

        try {
            result.throwOnFailure(
                separator = " | ",
                prefix = "FAILED: ",
                postfix = " END",
                transform = { "ERR: ${it.reason}" },
            )
            error("Expected ValidationException to be thrown")
        } catch (e: ValidationException) {
            assertEquals(violationList, e.violations)
            assertTrue(e.message!!.contains("FAILED:"))
            assertTrue(e.message!!.contains("ERR: error1"))
            assertTrue(e.message!!.contains(" | "))
            assertTrue(e.message!!.contains(" END"))
        }
    }

    @Test
    fun throwOnFailureWithEmptyViolations() {
        val result = ValidationResult.Invalid(emptyList())

        try {
            result.throwOnFailure(prefixOnEmpty = "No violations found")
            error("Expected ValidationException to be thrown")
        } catch (e: ValidationException) {
            assertTrue(e.message!!.contains("No violations found"))
        }
    }

    @Test
    fun validResultToString() {
        val result = ValidationResult.Valid

        assertEquals("ValidationResult.Valid", result.toString())
    }

    @Test
    fun invalidResultToString() {
        val result = ValidationResult.Invalid(violationList)

        val string = result.toString()
        assertTrue(string.contains("ValidationResult.Invalid"))
        assertTrue(string.contains("violations="))
    }
}
