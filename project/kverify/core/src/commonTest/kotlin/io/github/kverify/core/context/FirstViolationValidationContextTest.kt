package io.github.kverify.core.context

import io.github.kverify.core.exception.ThrowingValidationContextException
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.util.FailingRule
import io.github.kverify.core.util.PassingRule
import io.github.kverify.core.util.toFailingRules
import io.github.kverify.core.util.violations
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail

class FirstViolationValidationContextTest {
    private val violations = violations("error1", "error2", "error3")
    private val rules = violations.toFailingRules<String>()

    @Test
    fun firstViolationIsNullInitially() {
        val context = FirstViolationValidationContextImpl()
        assertNull(context.firstViolation)
    }

    @Test
    fun onFailureSetsFirstViolation() {
        val context = FirstViolationValidationContextImpl()
        val violation = violation("error")

        context.onFailure(violation)

        assertEquals(violation, context.firstViolation)
    }

    @Test
    fun onFailureIgnoresSubsequentViolations() {
        val context = FirstViolationValidationContextImpl()
        val firstViolation = violation("error1")
        val secondViolation = violation("error2")

        context.onFailure(firstViolation)
        context.onFailure(secondViolation)

        assertEquals(firstViolation, context.firstViolation)
    }

    @Test
    fun applyRuleExecutesRuleAndReturnsValue() {
        val context = FirstViolationValidationContextImpl()
        val violation = violation("error")
        val rule = FailingRule<String>(violation)
        val value = "test"

        val result = with(context) { value applyRule rule }

        assertEquals(value, result)
        assertEquals(violation, context.firstViolation)
    }

    @Test
    fun applyRuleSkipsExecutionWhenFirstViolationExists() {
        val context = FirstViolationValidationContextImpl()
        val firstViolation = violation("error1")

        val rule = Rule<String> { fail("Rules should not be executed when first violation exists") }
        val value = "test"

        context.onFailure(firstViolation)
        val result = with(context) { value applyRule rule }

        assertEquals(value, result)
        assertEquals(firstViolation, context.firstViolation)
    }

    @Test
    fun runRulesStopsAtFirstViolation() {
        val context = FirstViolationValidationContextImpl()
        val value = "test"
        val violation = violations.first()
        val rules =
            listOf<Rule<String>>(
                FailingRule(violation),
                Rule { fail("Rules should not be executed when first violation exists") },
            )

        val result =
            with(context) {
                runRules(value, rules.iterator())
            }

        assertEquals(value, result)
        assertEquals(violation, context.firstViolation)
    }

    @Test
    fun validateFirstReturnsValidWhenNoViolations() {
        val result = validateFirst { /* No violations */ }

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun validateFirstReturnsInvalidWhenViolationExists() {
        val violation = violation("error")

        val result = validateFirst { onFailure(violation) }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun validateFirstStopsAtFirstViolation() {
        val result = validateFirst { "test" applyRules rules }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violations.first(), result.violations.single())
    }

    @Test
    fun runValidatingFirstReturnsSuccessWhenNoViolations() {
        val value = "test value"
        val result = runValidatingFirst { value }

        assertTrue(result.isSuccess)
        assertEquals(value, result.getOrNull())
    }

    @Test
    fun runValidatingFirstReturnsFailureWhenViolationExists() {
        val violation = violation("error")

        val result =
            runValidatingFirst {
                onFailure(violation)
                "test value"
            }

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertIs<ThrowingValidationContextException>(exception)
        assertEquals(violation, exception.violation)
    }

    @Test
    fun runValidatingFirstStopsAtFirstViolation() {
        val result =
            runValidatingFirst {
                "test" applyRules rules
                "result value"
            }

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertIs<ThrowingValidationContextException>(exception)
        assertEquals(violations.first(), exception.violation)
    }

    @Test
    fun validateFirstWithRuleValidatesSingleRule() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test" validateFirst rule

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun validateFirstWithIterableStopsAtFirstViolation() {
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val result = "test" validateFirst rules

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violations.first(), result.violations.single())
    }

    @Test
    fun validateFirstWithVarargStopsAtFirstViolation() {
        val result = "test".validateFirst(*rules.toTypedArray())

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violations.first(), result.violations.single())
    }

    @Test
    fun validateFirstWithIteratorStopsAtFirstViolation() {
        val result = "test" validateFirst rules.iterator()

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violations.first(), result.violations.single())
    }

    @Test
    fun satisfiesReturnsTrueWhenNoViolations() {
        val result = "test" satisfies PassingRule

        assertTrue(result)
    }

    @Test
    fun satisfiesReturnsFalseWhenViolationExists() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test" satisfies rule

        assertFalse(result)
    }

    @Test
    fun satisfiesWithIterableReturnsTrueWhenNoViolations() {
        val rules: Iterable<Rule<String>> = emptyList()

        val result = "test" satisfies rules

        assertTrue(result)
    }

    @Test
    fun satisfiesWithIterableReturnsFalseWhenViolationExists() {
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val result = "test" satisfies rules

        assertFalse(result)
    }

    @Test
    fun satisfiesWithVarargReturnsTrueWhenNoViolations() {
        val result = "test".satisfies()

        assertTrue(result)
    }

    @Test
    fun satisfiesWithVarargReturnsFalseWhenViolationExists() {
        val result = "test".satisfies(*rules.toTypedArray())

        assertFalse(result)
    }

    @Test
    fun satisfiesWithIteratorReturnsTrueWhenNoViolations() {
        val result = "test".satisfies(emptyList<Rule<String>>().iterator())

        assertTrue(result)
    }

    @Test
    fun satisfiesWithIteratorReturnsFalseWhenViolationExists() {
        val result = "test".satisfies(rules.iterator())

        assertFalse(result)
    }

    @Test
    fun notSatisfiesReturnsFalseWhenNoViolations() {
        val result = "test" notSatisfies PassingRule

        assertFalse(result)
    }

    @Test
    fun notSatisfiesReturnsTrueWhenViolationExists() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test" notSatisfies rule

        assertTrue(result)
    }

    @Test
    fun notSatisfiesWithIterableReturnsFalseWhenNoViolations() {
        val rules: Iterable<Rule<String>> = emptyList()

        val result = "test" notSatisfies rules

        assertFalse(result)
    }

    @Test
    fun notSatisfiesWithIterableReturnsTrueWhenViolationExists() {
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val result = "test" notSatisfies rules

        assertTrue(result)
    }

    @Test
    fun notSatisfiesWithVarargReturnsFalseWhenNoViolations() {
        val result = "test".notSatisfies()

        assertFalse(result)
    }

    @Test
    fun notSatisfiesWithVarargReturnsTrueWhenViolationExists() {
        val result = "test".notSatisfies(*rules.toTypedArray())

        assertTrue(result)
    }

    @Test
    fun notSatisfiesWithIteratorReturnsFalseWhenNoViolations() {
        val result = "test".notSatisfies(emptyList<Rule<String>>().iterator())

        assertFalse(result)
    }

    @Test
    fun notSatisfiesWithIteratorReturnsTrueWhenViolationExists() {
        val result = "test".notSatisfies(rules.iterator())

        assertTrue(result)
    }

    @Test
    fun satisfiesWorksWithAnyTypeIncludingNull() {
        val value: Any? = null

        val result = value satisfies PassingRule

        assertTrue(result)
    }
}

@Suppress("UnusedPrivateClass", "Unused")
private class FirstViolationValidationContextCanBeImplemented : FirstViolationValidationContext {
    override val firstViolation: Violation? = null

    override fun onFailure(violation: Violation): Unit = Unit
}
