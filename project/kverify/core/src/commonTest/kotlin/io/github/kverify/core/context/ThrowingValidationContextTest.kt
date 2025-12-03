package io.github.kverify.core.context

import io.github.kverify.core.exception.ThrowingValidationContextException
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.util.FailingRule
import io.github.kverify.core.util.PassingRule
import io.github.kverify.core.util.violations
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertSame
import kotlin.test.assertTrue

class ThrowingValidationContextTest {
    val context = ThrowingValidationContext()
    val violation = violation("error")

    @Test
    fun throwingValidationContextFactoryCreatesContext() {
        val context = ThrowingValidationContext()

        assertFailsWith<ThrowingValidationContextException> {
            context.onFailure(violation)
        }
        assertSame(context, ThrowingValidationObject)
        assertIs<ThrowingValidationContext>(context)
    }

    @Test
    fun onFailureThrowsException() {
        val exception =
            assertFailsWith<ThrowingValidationContextException> {
                context.onFailure(violation)
            }

        assertEquals(violation, exception.violation)
    }

    @Test
    fun failIfThrowsWhenConditionTrue() {
        val exception =
            assertFailsWith<ThrowingValidationContextException> {
                context.failIf(true) { violation }
            }

        assertEquals(violation, exception.violation)
    }

    @Test
    fun failIfDoesNotThrowWhenConditionFalse() {
        context.failIf(false) { violation }
    }

    @Test
    fun failIfAllowsSmartCast() {
        val value: Any = "test"

        context.failIf(value !is String) { violation }

        acceptString(value)
    }

    @Test
    fun failIfNotThrowsWhenConditionFalse() {
        val exception =
            assertFailsWith<ThrowingValidationContextException> {
                context.failIfNot(false) { violation }
            }

        assertEquals(violation, exception.violation)
    }

    @Test
    fun failIfNotDoesNotThrowWhenConditionTrue() {
        context.failIfNot(true) { violation }
    }

    @Test
    fun failIfNotAllowsSmartCast() {
        val value: Any = "test"

        context.failIfNot(value is String) { violation }

        acceptString(value)
    }

    @Test
    fun verifyThrowingWithNoViolations() {
        val result =
            verifyThrowing {
                "test" verifyWith PassingRule
            }

        assertEquals("test", result)
    }

    @Test
    fun verifyThrowingThrowsOnViolation() {
        val rule = FailingRule<String>(violation)

        assertFailsWith<ThrowingValidationContextException> {
            verifyThrowing {
                "test" verifyWith rule
            }
        }
    }

    @Test
    fun valueVerifyWithThrowingNoRules() {
        val result = "test".verifyWithThrowing()

        assertEquals("test", result)
    }

    @Test
    fun valueVerifyWithThrowingSingleRule() {
        val rule = FailingRule<String>(violation)

        assertFailsWith<ThrowingValidationContextException> {
            "test" verifyWithThrowing rule
        }
    }

    @Test
    fun valueVerifyWithThrowingVarargRules() {
        val rule = FailingRule<String>(violation)

        assertFailsWith<ThrowingValidationContextException> {
            "test".verifyWithThrowing(PassingRule, rule)
        }
    }

    @Test
    fun valueVerifyWithThrowingIterableRules() {
        val rules =
            listOf(
                PassingRule as Rule<String>,
                FailingRule(violation),
            )

        assertFailsWith<ThrowingValidationContextException> {
            "test" verifyWithThrowing rules
        }
    }

    @Test
    fun valueVerifyWithThrowingPassingRule() {
        val result = "test" verifyWithThrowing PassingRule

        assertEquals("test", result)
    }

    @Test
    fun verifyFailFastWithNoViolations() {
        val result =
            verifyFailFast {
                "test" verifyWith PassingRule
            }

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun verifyFailFastWithViolation() {
        val rule = FailingRule<String>(violation)

        val result =
            verifyFailFast {
                "test" verifyWith rule
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun verifyFailFastStopsAtFirstViolation() {
        val violationList = violations("error1", "error2")
        val rules = violationList.map { FailingRule<String>(it) }
        var secondRuleExecuted = false

        val result =
            verifyFailFast {
                "test" verifyWith rules[0]
                secondRuleExecuted = true
                "test" verifyWith rules[1]
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList[0], result.violations.single())
        assertFalse(secondRuleExecuted)
    }

    @Test
    fun valueVerifyWithFailFastNoRules() {
        val result = "test".verifyWithFailFast()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun valueVerifyWithFailFastSingleRule() {
        val rule = FailingRule<String>(violation)

        val result = "test" verifyWithFailFast rule

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun valueVerifyWithFailFastVarargRules() {
        val violationList = violations("error1", "error2")
        val rules = violationList.map { FailingRule<String>(it) }

        val result = "test".verifyWithFailFast(rules[0], rules[1])

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList[0], result.violations.single())
    }

    @Test
    fun valueVerifyWithFailFastIterableRules() {
        val violationList = violations("error1", "error2")
        val rules = violationList.map { FailingRule<String>(it) }

        val result = "test" verifyWithFailFast rules

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList[0], result.violations.single())
    }

    @Test
    fun passesFailFastWithNoRules() {
        val result = "test".passesFailFast()

        assertTrue(result)
    }

    @Test
    fun passesFailFastWithPassingRule() {
        val result = "test" passesFailFast PassingRule

        assertTrue(result)
    }

    @Test
    fun passesFailFastWithFailingRule() {
        val rule = FailingRule<String>(violation)

        val result = "test" passesFailFast rule

        assertFalse(result)
    }

    @Test
    fun passesFailFastWithVarargRules() {
        val rule = FailingRule<String>(violation)

        val result = "test".passesFailFast(PassingRule, rule)

        assertFalse(result)
    }

    @Test
    fun passesFailFastWithIterableRules() {
        val rules =
            listOf(
                PassingRule as Rule<String>,
                FailingRule(violation),
            )

        val result = "test" passesFailFast rules

        assertFalse(result)
    }

    @Test
    fun notPassesFailFastWithNoRules() {
        val result = "test".notPassesFailFast()

        assertFalse(result)
    }

    @Test
    fun notPassesFailFastWithPassingRule() {
        val result = "test" notPassesFailFast PassingRule

        assertFalse(result)
    }

    @Test
    fun notPassesFailFastWithFailingRule() {
        val rule = FailingRule<String>(violation)

        val result = "test" notPassesFailFast rule

        assertTrue(result)
    }

    @Test
    fun notPassesFailFastWithVarargRules() {
        val rule = FailingRule<String>(violation)

        val result = "test".notPassesFailFast(PassingRule, rule)

        assertTrue(result)
    }

    @Test
    fun notPassesFailFastWithIterableRules() {
        val rules =
            listOf(
                PassingRule as Rule<String>,
                FailingRule(violation),
            )

        val result = "test" notPassesFailFast rules

        assertTrue(result)
    }

    @Test
    fun verifyThrowingReturnsValue() {
        val result =
            verifyThrowing {
                verify("test value")
            }

        assertEquals("test value", result)
    }
}

@Suppress("UNUSED_PARAMETER", "NOTHING_TO_INLINE", "SameParameterValue")
private inline fun acceptString(string: String): Unit = Unit
