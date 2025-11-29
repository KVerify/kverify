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
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.test.fail

class ThrowingValidationContextTest {
    private val violations = violations("error1", "error2", "error3")
    private val rules = violations.toFailingRules<String>()

    @Test
    fun throwingValidationContextIsFunctionalInterface() {
        val violation = violations.first()
        val context = ThrowingValidationContext { throw ThrowingValidationContextException(it) }

        val exception =
            assertFailsWith<ThrowingValidationContextException> {
                context.onFailure(violation)
            }

        assertEquals(violation, exception.violation)
    }

    @Test
    fun throwingValidationContextOnFailureThrowsException() {
        val violation = violations.first()

        val exception =
            assertFailsWith<ThrowingValidationContextException> {
                ThrowingValidationObject.onFailure(violation)
            }

        assertEquals(violation, exception.violation)
    }

    @Test
    fun failIfThrowsWhenConditionIsTrue() {
        val violation = violation("error")
        val context = ThrowingValidationObject

        val exception =
            assertFailsWith<ThrowingValidationContextException> {
                context.failIf(true) { violation }
            }

        assertEquals(violation, exception.violation)
    }

    @Test
    fun failIfDoesNotThrowWhenConditionIsFalse() {
        val context = ThrowingValidationObject

        context.failIf(false) { violation("error") }
    }

    @Test
    fun failIfNotThrowsWhenConditionIsFalse() {
        val violation = violation("error")
        val context = ThrowingValidationObject

        val exception =
            assertFailsWith<ThrowingValidationContextException> {
                context.failIfNot(false) { violation }
            }

        assertEquals(violation, exception.violation)
    }

    @Test
    fun failIfNotDoesNotThrowWhenConditionIsTrue() {
        val context = ThrowingValidationObject

        context.failIfNot(true) { violation("error") }
    }

    @Test
    fun failIfLazyViolationIsOnlyEvaluatedWhenConditionIsTrue() {
        var evaluated = false
        val context = ThrowingValidationObject
        val violation = violation("error")

        context.failIf(false) {
            evaluated = true
            violation
        }

        assertFalse(evaluated)

        assertFailsWith<ThrowingValidationContextException> {
            context.failIf(true) {
                evaluated = true
                violation
            }
        }

        assertTrue(evaluated)
    }

    @Test
    fun failIfNotLazyViolationIsOnlyEvaluatedWhenConditionIsFalse() {
        var evaluated = false
        val context = ThrowingValidationObject
        val violation = violation("error")

        context.failIfNot(true) {
            evaluated = true
            violation
        }

        assertFalse(evaluated)

        assertFailsWith<ThrowingValidationContextException> {
            context.failIfNot(false) {
                evaluated = true
                violation
            }
        }

        assertTrue(evaluated)
    }

    @Test
    fun failIfSmartCast() {
        val context = ThrowingValidationObject
        val value: Any? = "string"

        context.failIf(value !is String) { violation("error") }

        val test: String = value
    }

    @Test
    fun failIfNotSmartCast() {
        val context = ThrowingValidationObject
        val value: Any? = "string"

        context.failIfNot(value is String) { violation("error") }

        val test: String = value
    }

    @Test
    fun validateOrThrowReturnsValueWhenNoViolations() {
        val value = "test value"
        val result = validateOrThrow { value }

        assertEquals(value, result)
    }

    @Test
    fun validateOrThrowThrowsWhenViolationOccurs() {
        val violation = violation("error")

        val exception =
            assertFailsWith<ThrowingValidationContextException> {
                validateOrThrow { onFailure(violation) }
            }

        assertEquals(violation, exception.violation)
    }

    @Test
    fun validateOrThrowWithRuleReturnsValueWhenRulePasses() {
        val value = "test"

        val result = value validateOrThrow PassingRule

        assertEquals(value, result)
    }

    @Test
    fun validateOrThrowWithRuleThrowsWhenRuleFails() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val exception =
            assertFailsWith<ThrowingValidationContextException> {
                "test" validateOrThrow rule
            }

        assertEquals(violation, exception.violation)
    }

    @Test
    fun validateOrThrowWithIteratorThrowsOnFirstFailure() {
        val exception =
            assertFailsWith<ThrowingValidationContextException> {
                "test" validateOrThrow rules.iterator()
            }

        assertEquals(violations.first(), exception.violation)
    }

    @Test
    fun validateOrThrowWithIterableThrowsOnFirstFailure() {
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val exception =
            assertFailsWith<ThrowingValidationContextException> {
                "test" validateOrThrow rules
            }

        assertEquals(violations.first(), exception.violation)
    }

    @Test
    fun validateOrThrowWithVarargThrowsOnFirstFailure() {
        val exception =
            assertFailsWith<ThrowingValidationContextException> {
                "test".validateOrThrow(*rules.toTypedArray())
            }

        assertEquals(violations.first(), exception.violation)
    }

    @Test
    fun validateFailFastReturnsValidWhenNoViolations() {
        val result = validateFailFast { /* No violations */ }

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun validateFailFastReturnsInvalidWhenViolationOccurs() {
        val violation = violation("error")
        val result = validateFailFast { onFailure(violation) }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun validateFailFastStopsOnFirstViolation() {
        val result =
            validateFailFast {
                "test".applyRules(*rules.toTypedArray())
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violations.first(), result.violations.single())
    }

    @Test
    fun runValidatingFailFastReturnsSuccessWhenNoViolations() {
        val value = "test value"
        val result = runValidatingFailFast { value }

        assertTrue(result.isSuccess)
        assertEquals(value, result.getOrNull())
    }

    @Test
    fun runValidatingFailFastReturnsFailureWhenViolationOccurs() {
        val violation = violation("error")
        val result =
            runValidatingFailFast {
                onFailure(violation)
                "test value"
            }

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertIs<ThrowingValidationContextException>(exception)
        assertEquals(violation, exception.violation)
    }

    @Test
    fun validateFailFastWithRuleReturnsValidWhenRulePasses() {
        val result = "test" validateFailFast PassingRule

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun validateFailFastWithRuleReturnsInvalidWhenRuleFails() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test" validateFailFast rule

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun validateFailFastWithIterableReturnsInvalidOnFirstFailure() {
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val result = "test" validateFailFast rules

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violations.first(), result.violations.single())
    }

    @Test
    fun validateFailFastWithVarargReturnsInvalidOnFirstFailure() {
        val result = "test".validateFailFast(*rules.toTypedArray())

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violations.first(), result.violations.single())
    }

    @Test
    fun validateFailFastWithIteratorReturnsInvalidOnFirstFailure() {
        val result = "test" validateFailFast rules.iterator()

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violations.first(), result.violations.single())
    }

    @Test
    fun satisfiesFailFastReturnsTrueWhenRulePasses() {
        val result = "test" satisfiesFailFast PassingRule

        assertTrue(result)
    }

    @Test
    fun satisfiesFailFastReturnsFalseWhenRuleFails() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test" satisfiesFailFast rule

        assertFalse(result)
    }

    @Test
    fun satisfiesFailFastWithIterableReturnsTrueWhenAllRulesPass() {
        val passingRules: Iterable<Rule<String>> = listOf(PassingRule)

        val result = "test" satisfiesFailFast passingRules

        assertTrue(result)
    }

    @Test
    fun satisfiesFailFastWithIterableReturnsFalseWhenAnyRuleFails() {
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val result = "test" satisfiesFailFast rules

        assertFalse(result)
    }

    @Test
    fun satisfiesFailFastWithVarargReturnsTrueWhenAllRulesPass() {
        val result = "test".satisfiesFailFast(PassingRule)

        assertTrue(result)
    }

    @Test
    fun satisfiesFailFastWithVarargReturnsFalseWhenAnyRuleFails() {
        val result = "test".satisfiesFailFast(*rules.toTypedArray())

        assertFalse(result)
    }

    @Test
    fun satisfiesFailFastWithIteratorReturnsTrueWhenAllRulesPass() {
        val passingRules = listOf(PassingRule)

        val result = "test".satisfiesFailFast(passingRules.iterator())

        assertTrue(result)
    }

    @Test
    fun satisfiesFailFastWithIteratorReturnsFalseWhenAnyRuleFails() {
        val result = "test".satisfiesFailFast(rules.iterator())

        assertFalse(result)
    }

    @Test
    fun satisfiesFailFastStopsAtTheFirstFailure() {
        var executedAfterFailure = false
        val failingRule =
            Rule<String> {
                onFailure(violation("error"))
                executedAfterFailure = true
            }

        "test" satisfiesFailFast failingRule
        assertFalse(executedAfterFailure)
    }

    @Test
    fun notSatisfiesFailFastReturnsFalseWhenRulePasses() {
        val result = "test" notSatisfiesFailFast PassingRule

        assertFalse(result)
    }

    @Test
    fun notSatisfiesFailFastReturnsTrueWhenRuleFails() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test" notSatisfiesFailFast rule

        assertTrue(result)
    }

    @Test
    fun notSatisfiesFailFastWithIterableReturnsFalseWhenAllRulesPass() {
        val passingRules: Iterable<Rule<String>> = listOf(PassingRule)

        val result = "test" notSatisfiesFailFast passingRules

        assertFalse(result)
    }

    @Test
    fun notSatisfiesFailFastWithIterableReturnsTrueWhenAnyRuleFails() {
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val result = "test" notSatisfiesFailFast rules

        assertTrue(result)
    }

    @Test
    fun notSatisfiesFailFastWithVarargReturnsFalseWhenAllRulesPass() {
        val result = "test".notSatisfiesFailFast(PassingRule)

        assertFalse(result)
    }

    @Test
    fun notSatisfiesFailFastWithVarargReturnsTrueWhenAnyRuleFails() {
        val result = "test".notSatisfiesFailFast(*rules.toTypedArray())

        assertTrue(result)
    }

    @Test
    fun notSatisfiesFailFastWithIteratorReturnsFalseWhenAllRulesPass() {
        val passingRules = listOf(PassingRule)

        val result = "test".notSatisfiesFailFast(passingRules.iterator())

        assertFalse(result)
    }

    @Test
    fun notSatisfiesFailFastWithIteratorReturnsTrueWhenAnyRuleFails() {
        val result = "test".notSatisfiesFailFast(rules.iterator())

        assertTrue(result)
    }

    @Test
    fun notSatisfiesFailFastStopsAtTheFirstFailure() {
        var executedAfterFailure = false
        val failingRule =
            Rule<String> {
                onFailure(violation("error"))
                executedAfterFailure = true
            }

        "test" notSatisfiesFailFast failingRule
        assertFalse(executedAfterFailure)
    }

    @Test
    fun throwingValidationContextStopsExecutionOnFirstFailure() {
        var executedAfterFailure = false
        val failingRule =
            Rule<String> {
                onFailure(violation("error"))
                executedAfterFailure = true
            }

        assertFailsWith<ThrowingValidationContextException> {
            validateOrThrow {
                "test" applyRule failingRule
            }
        }

        assertFalse(executedAfterFailure)
    }
}

@Suppress("UnusedPrivateClass", "Unused")
private class ThrowingValidationContextCanBeImplemented : ThrowingValidationContext {
    override fun onFailure(violation: Violation): Nothing = throw ThrowingValidationContextException(violation)
}
