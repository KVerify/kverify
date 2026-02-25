package io.github.kverify.core.result

import io.github.kverify.core.rule.Violation
import io.github.kverify.core.rule.ViolationReason
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ValidationResultTest {
    private val violation1 = ViolationReason("error1")
    private val violation2 = ViolationReason("error2")
    private val violation3 = ViolationReason("error3")

    // Valid

    @Test
    fun validIsValid() {
        assertTrue(ValidationResult.Valid.isValid)
        assertFalse(ValidationResult.Valid.isInvalid)
    }

    @Test
    fun validToString() {
        assertEquals("ValidationResult.Valid", ValidationResult.Valid.toString())
    }

    // Invalid

    @Test
    fun invalidIsInvalid() {
        val result = ValidationResult.Invalid(violation1)

        assertFalse(result.isValid)
        assertTrue(result.isInvalid)
    }

    @Test
    fun invalidWithSingleViolation() {
        val result = ValidationResult.Invalid(violation1)

        assertEquals(listOf(violation1), result.violations)
    }

    @Test
    fun invalidWithVarargViolations() {
        val result = ValidationResult.Invalid(violation1, violation2)

        assertEquals(listOf(violation1, violation2), result.violations)
    }

    // Factory functions

    @Test
    fun factoryNoArgsReturnsValid() {
        val result = ValidationResult()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun factoryWithSingleViolationReturnsInvalid() {
        val result = ValidationResult(violation1)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation1), result.violations)
    }

    @Test
    fun factoryWithEmptyListReturnsValid() {
        val result = ValidationResult(emptyList())

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun factoryWithNonEmptyListReturnsInvalid() {
        val result = ValidationResult(listOf(violation1, violation2))

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(2, result.violations.size)
    }

    // violationsOrEmpty

    @Test
    fun violationsOrEmptyReturnsViolationsForInvalid() {
        val result = ValidationResult.Invalid(violation1)

        assertEquals(listOf(violation1), result.violationsOrEmpty())
    }

    @Test
    fun violationsOrEmptyReturnsEmptyForValid() {
        assertEquals(emptyList(), ValidationResult.Valid.violationsOrEmpty())
    }

    // violationsOrNull

    @Test
    fun violationsOrNullReturnsViolationsForInvalid() {
        val result = ValidationResult.Invalid(violation1)

        assertEquals(listOf(violation1), result.violationsOrNull())
    }

    @Test
    fun violationsOrNullReturnsNullForValid() {
        assertNull(ValidationResult.Valid.violationsOrNull())
    }

    // toValidationResult

    @Test
    fun violationToValidationResult() {
        val result = violation1.toValidationResult()

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation1), result.violations)
    }

    @Test
    fun nullViolationToValidationResult() {
        val violation: ViolationReason? = null
        val result = violation.toValidationResult()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun nonNullViolationToValidationResult() {
        val violation: ViolationReason? = violation1
        val result = violation.toValidationResult()

        assertIs<ValidationResult.Invalid>(result)
    }

    // plus(ValidationResult)

    @Test
    fun validPlusValidReturnsValid() {
        val result = ValidationResult.Valid + ValidationResult.Valid

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun validPlusInvalidReturnsInvalid() {
        val invalid = ValidationResult.Invalid(violation1)
        val result = ValidationResult.Valid + invalid

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation1), result.violations)
    }

    @Test
    fun invalidPlusValidReturnsInvalid() {
        val invalid = ValidationResult.Invalid(violation1)
        val result = invalid + ValidationResult.Valid

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation1), result.violations)
    }

    @Test
    fun invalidPlusInvalidCombinesViolations() {
        val invalid1 = ValidationResult.Invalid(violation1)
        val invalid2 = ValidationResult.Invalid(violation2)

        val result = invalid1 + invalid2

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation1, violation2), result.violations)
    }

    // plus(Violation)

    @Test
    fun validPlusViolationReturnsInvalid() {
        val result = ValidationResult.Valid + violation1

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation1), result.violations)
    }

    @Test
    fun invalidPlusViolationAppendsViolation() {
        val invalid = ValidationResult.Invalid(violation1)
        val result = invalid + violation2

        assertEquals(listOf(violation1, violation2), result.violations)
    }

    // plus(List<Violation>)

    @Test
    fun plusEmptyViolationListReturnsSame() {
        val valid: ValidationResult = ValidationResult.Valid
        val result = valid + emptyList<Violation>()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun validPlusViolationListReturnsInvalid() {
        val valid: ValidationResult = ValidationResult.Valid
        val result = valid + listOf(violation1, violation2)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(2, result.violations.size)
    }

    @Test
    fun invalidPlusViolationListCombines() {
        val invalid: ValidationResult = ValidationResult.Invalid(violation1)
        val result = invalid + listOf(violation2, violation3)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(3, result.violations.size)
    }

    // plus(List<ValidationResult>)

    @Test
    fun plusEmptyResultListReturnsSame() {
        val valid: ValidationResult = ValidationResult.Valid
        val result = valid + emptyList<ValidationResult>()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun validPlusResultListWithInvalidReturnsInvalid() {
        val valid: ValidationResult = ValidationResult.Valid
        val results =
            listOf(
                ValidationResult.Invalid(violation1),
                ValidationResult.Valid,
                ValidationResult.Invalid(violation2),
            )

        val result = valid + results

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation1, violation2), result.violations)
    }

    @Test
    fun validPlusResultListWithOnlyValidReturnsValid() {
        val valid: ValidationResult = ValidationResult.Valid
        val results = listOf(ValidationResult.Valid, ValidationResult.Valid)

        val result = valid + results

        assertIs<ValidationResult.Valid>(result)
    }

    // mergeValidationResults

    @Test
    fun mergeEmptyListReturnsValid() {
        val result = emptyList<ValidationResult>().mergeValidationResults()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun mergeOnlyValidReturnsValid() {
        val result =
            listOf(
                ValidationResult.Valid,
                ValidationResult.Valid,
            ).mergeValidationResults()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun mergeMixedFlattensViolations() {
        val result =
            listOf(
                ValidationResult.Invalid(violation1, violation2),
                ValidationResult.Valid,
                ValidationResult.Invalid(violation3),
            ).mergeValidationResults()

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation1, violation2, violation3), result.violations)
    }

    // ifValid / ifInvalid

    @Test
    fun ifValidExecutesBlockForValid() {
        val result = ValidationResult.Valid.ifValid { "success" }

        assertEquals("success", result)
    }

    @Test
    fun ifValidReturnsNullForInvalid() {
        val result = ValidationResult.Invalid(violation1).ifValid { "success" }

        assertNull(result)
    }

    @Test
    fun ifInvalidExecutesBlockForInvalid() {
        val result = ValidationResult.Invalid(violation1).ifInvalid { it.size }

        assertEquals(1, result)
    }

    @Test
    fun ifInvalidReturnsNullForValid() {
        val result = ValidationResult.Valid.ifInvalid { it.size }

        assertNull(result)
    }

    // fold

    @Test
    fun foldExecutesIfValidForValid() {
        val result =
            ValidationResult.Valid.fold(
                ifValid = { "valid" },
                ifInvalid = { "invalid" },
            )

        assertEquals("valid", result)
    }

    @Test
    fun foldExecutesIfInvalidForInvalid() {
        val result =
            ValidationResult.Invalid(violation1, violation2).fold(
                ifValid = { -1 },
                ifInvalid = { it.size },
            )

        assertEquals(2, result)
    }

    // onValid / onInvalid

    @Test
    fun onValidExecutesBlockAndReturnsSelf() {
        var executed = false
        val original: ValidationResult = ValidationResult.Valid

        val result = original.onValid { executed = true }

        assertTrue(executed)
        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun onValidDoesNotExecuteForInvalid() {
        var executed = false

        ValidationResult.Invalid(violation1).onValid { executed = true }

        assertFalse(executed)
    }

    @Test
    fun onInvalidExecutesBlockAndReturnsSelf() {
        var capturedSize = -1
        val original: ValidationResult = ValidationResult.Invalid(violation1, violation2)

        val result = original.onInvalid { capturedSize = it.size }

        assertEquals(2, capturedSize)
        assertIs<ValidationResult.Invalid>(result)
    }

    @Test
    fun onInvalidDoesNotExecuteForValid() {
        var executed = false

        ValidationResult.Valid.onInvalid { executed = true }

        assertFalse(executed)
    }

    // throwOnFailure

    @Test
    fun throwOnFailureDoesNothingForValid() {
        ValidationResult.Valid.throwOnFailure()
    }

    @Test
    fun throwOnFailureThrowsForInvalid() {
        val result: ValidationResult = ValidationResult.Invalid(violation1)

        val exception =
            try {
                result.throwOnFailure()
                null
            } catch (e: ValidationException) {
                e
            }

        assertIs<ValidationException>(exception)
        assertEquals(listOf(violation1), exception.violations)
    }

    @Test
    fun throwOnFailureUsesCustomFormatting() {
        val result: ValidationResult = ValidationResult.Invalid(violation1, violation2)

        val exception =
            try {
                result.throwOnFailure(
                    separator = ", ",
                    prefix = "Errors: ",
                    transform = { it.reason },
                )
                null
            } catch (e: ValidationException) {
                e
            }

        assertIs<ValidationException>(exception)
        assertEquals("Errors: error1, error2", exception.message)
    }
}
