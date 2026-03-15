package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ViolationException
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.fail

class ThrowingValidationScopeTest {
    @Test
    fun enforceDoesNotThrowWhenRulePasses() {
        val scope = ThrowingValidationScope(EmptyValidationContext)

        scope.enforce { null }
    }

    @Test
    fun enforceThrowsViolationExceptionWhenRuleFails() {
        val scope = ThrowingValidationScope(EmptyValidationContext)

        val exception =
            try {
                scope.enforce { violation("must be positive") }
                fail("Expected ViolationException to be thrown")
            } catch (e: ViolationException) {
                e
            }

        assertIs<ViolationException>(exception)
    }

    @Test
    fun enforceExceptionCarriesExactViolation() {
        val scope = ThrowingValidationScope(EmptyValidationContext)
        val v = violation("specific reason")

        val exception =
            try {
                scope.enforce { v }
                fail("Expected ViolationException to be thrown")
            } catch (e: ViolationException) {
                e
            }

        assertEquals(v, exception.violation)
    }

    @Test
    fun enforceStopsAfterFirstFailure() {
        val scope = ThrowingValidationScope(EmptyValidationContext)
        var secondRuleEvaluated = false

        try {
            scope.enforce { violation("first") }
            scope.enforce {
                secondRuleEvaluated = true
                null
            }
        } catch (_: ViolationException) {
        }

        assertEquals(false, secondRuleEvaluated)
    }

    @Test
    fun validationContextIsStoredAsProvided() {
        val context = NamePathElement("field")

        val scope = ThrowingValidationScope(context)

        assertEquals(listOf(context), scope.validationContext.toList())
    }

    @Test
    fun validateThrowingReturnsBlockResultWhenAllRulesPass() {
        val expected = 42

        val result = validateThrowing { expected }

        assertEquals(expected, result)
    }

    @Test
    fun validateThrowingThrowsOnFirstFailingRule() {
        val v = violation("reason")

        val exception =
            try {
                validateThrowing { enforce { v } }
                fail("Expected ViolationException to be thrown")
            } catch (e: ViolationException) {
                e
            }

        assertEquals(v, exception.violation)
    }

    @Test
    fun validateThrowingDoesNotEvaluateRulesAfterFirstFailure() {
        var secondRuleEvaluated = false

        try {
            validateThrowing {
                enforce { violation("first") }
                enforce {
                    secondRuleEvaluated = true
                    null
                }
            }
        } catch (_: ViolationException) {
        }

        assertEquals(false, secondRuleEvaluated)
    }

    @Test
    fun validateThrowingWithEmptyBlockCompletesWithoutThrowing() {
        validateThrowing {}
    }

    @Test
    fun validateThrowingPassesContextToScope() {
        val context = NamePathElement("root")
        var capturedContext: ValidationContext? = null

        validateThrowing(context) {
            capturedContext = validationContext
        }

        assertEquals(listOf(context), capturedContext?.toList())
    }

    @Test
    fun validateThrowingDefaultContextIsEmpty() {
        var capturedContext: ValidationContext? = null

        validateThrowing {
            capturedContext = validationContext
        }

        assertEquals(EmptyValidationContext, capturedContext)
    }
}
